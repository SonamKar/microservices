package demo.customermanagement.core.transformer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import demo.customermanagement.core.exception.util.MyExceptionBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import demo.customermanagement.core.dto.customerdetails.*;
import demo.customermanagement.core.dto.customerdetails.request.CreateCustomerRequest;
import demo.customermanagement.core.dto.kafka.consumedetails.customerCreateEvent;
import demo.customermanagement.core.dto.kafka.consumedetails.engagedParty_record;
import demo.customermanagement.core.dto.kafka.consumedetails.individualIdentification_record;
import demo.customermanagement.core.dto.kafka.producedetails.Account;
import demo.customermanagement.core.dto.kafka.producedetails.CustomerResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import demo.customermanagement.core.logging.JacksonIgnoreAvroPropertiesMixIn;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomerManagementMapper {

    private final ObjectMapper objectMapper;
    private final MyExceptionBuilder exceptionBuilder;
    private static final String JSON_PROCESSING_ERROR =
            "Error while processing Json Response payload";

    public CreateCustomerRequest constructCreateCustomerRequest(
            final customerCreateEvent customerCreateEvent) {
        try {
            List<ContactMedium> contactMediumList = Collections.singletonList(
                    ContactMedium.builder()
                            .mediumType(customerCreateEvent.getEvent().getCustomer().
                                    getEngagedParty().getContactMedium().getMediumType())
                            .build());

            List<PartyCharacteristic> characteristicList = Collections.singletonList(
                    PartyCharacteristic.builder()
                            .name(customerCreateEvent.getEvent().getCustomer().
                                    getEngagedParty().getPartyCharacteristic().getName())
                            .value(customerCreateEvent.getEvent().getCustomer().
                                    getEngagedParty().getPartyCharacteristic().getValue())
                            .build());

            individualIdentification_record individualIdentificationRecord = customerCreateEvent
                    .getEvent()
                    .getCustomer()
                    .getEngagedParty()
                    .getIndividualIdentification();

            List<IndividualIdentification> individualIdentificationList =
                    Collections.singletonList(
                            IndividualIdentification.builder()
                                    .identificationId(individualIdentificationRecord.getIdentificationId())
                                    .identificationType(individualIdentificationRecord.getIdentificationType())
                                    .issuingDate(individualIdentificationRecord.getIssuingDate())
                                    .placeOfIssue(individualIdentificationRecord.getPlaceOfIssue())
                                    .issuingAuthority(individualIdentificationRecord.getIssuingAuthority())
                                    .validFor(ValidFor.builder()
                                            .startDateTime(
                                                    individualIdentificationRecord.getValidFor().getStartDateTime())
                                            .endDateTime(individualIdentificationRecord.getValidFor().getEndDateTime())
                                            .build())
                                    .build());

            engagedParty_record engagedPartyRecord = customerCreateEvent
                    .getEvent()
                    .getCustomer()
                    .getEngagedParty();

            EngagedParty engagedParty = EngagedParty.builder()
                    .referredType(engagedPartyRecord.getAtReferredType())
                    .givenName(engagedPartyRecord.getGivenName())
                    .middleName(engagedPartyRecord.getMiddleName())
                    .familyName(engagedPartyRecord.getFamilyName())
                    .fullName(engagedPartyRecord.getFullName())
                    .gender(engagedPartyRecord.getGender())
                    .nationality(engagedPartyRecord.getNationality())
                    .birthDate(engagedPartyRecord.getBirthDate())
                    .contactMedium(contactMediumList)
                    .individualIdentification(individualIdentificationList)
                    .partyCharacteristic(characteristicList)
                    .build();
            log.info("The constructed engagedParty object to be sent to CreateCustomerFeign is :: {}"
                    , objectMapper.writeValueAsString(engagedParty));

            CreateCustomerRequest createCustomerRequest = CreateCustomerRequest.builder()
                    .name(customerCreateEvent
                            .getEvent()
                            .getCustomer()
                            .getName())
                    .engagedParty(engagedParty)
                    .build();
            log.info("The final constructed CreateCustomerFeign request is :: {}",
                    objectMapper.writeValueAsString(createCustomerRequest));

            return createCustomerRequest;

        } catch (JsonProcessingException exception) {
            log.info("Json processing exception occurred while parsing createCustomerRequest");
            exceptionBuilder.throwTechnicalException(
                    "Runtime Error",
                    "Runtime Error",
                    JSON_PROCESSING_ERROR,
                    exception.getLocalizedMessage());
        }
        return null;
    }

    public CustomerResponse mapToKafkaProduceDetails(
            demo.customermanagement.core.dto.customerdetails.response.CustomerResponse customerResponse) {
        try {
            CustomerResponse finalCustomerResponse = CustomerResponse.newBuilder()
                    .setId(customerResponse.getId())
                    .setName(customerResponse.getName())
                    .setEngagedParty(
                            demo.customermanagement.core.dto.kafka.producedetails.EngagedParty.newBuilder()
                                    .setAtReferredType(customerResponse.getEngagedParty().getReferredType())
                                    .setFullName(customerResponse.getEngagedParty().getFullName())
                                    .setGivenName(customerResponse.getEngagedParty().getGivenName())
                                    .setFamilyName(customerResponse.getEngagedParty().getFamilyName())
                                    .setMiddleName(customerResponse.getEngagedParty().getMiddleName())
                                    .setGender(customerResponse.getEngagedParty().getGender())
                                    .setBirthDate(customerResponse.getEngagedParty().getBirthDate())
                                    .setNationality(customerResponse.getEngagedParty().getNationality())
                                    .setContactMedium(constructContactMediumList(
                                            customerResponse.getEngagedParty().getContactMedium()))
                                    .setIndividualIdentification(
                                            constructIndividualIdentificationList(customerResponse.getEngagedParty()
                                                    .getIndividualIdentification()))
                                    .setPartyCharacteristic(
                                            constructPartyCharacteristicList(customerResponse.getEngagedParty()
                                                    .getPartyCharacteristic()))
                                    .build())
                    .setAccount(constructAccountList(customerResponse.getAccount()))
                    .build();

            log.info("Customer Response to be sent to Kafka is :: {}", objectMapper
                    .addMixIn(org.apache.avro.specific.SpecificRecord.class,
                            JacksonIgnoreAvroPropertiesMixIn.class).writeValueAsString(finalCustomerResponse));

        } catch (JsonProcessingException ex) {
            log.info("Json processing exception occurred while parsing customer response");
            exceptionBuilder.throwTechnicalException(
                    "Runtime Error",
                    "Runtime Error",
                    JSON_PROCESSING_ERROR,
                    ex.getLocalizedMessage());
        }
        return null;
    }

    public List<Account> constructAccountList(
            List<demo.customermanagement.core.dto.customerdetails.response.Account> accountList)
            throws JsonProcessingException {
        List<Account> produceDetailsAccountList = new ArrayList<>();
        accountList.forEach(account -> produceDetailsAccountList.add(
                Account.newBuilder()
                        .setId(account.getId())
                        .setAtReferredType(account.getReferredType())
                        .build()
        ));
        log.info("List of account to produce to kafka :: {}",
                objectMapper.addMixIn(org.apache.avro.specific.SpecificRecord.class,
                        JacksonIgnoreAvroPropertiesMixIn.class).writeValueAsString(produceDetailsAccountList));

        return produceDetailsAccountList;
    }

    public List<demo.customermanagement.core.dto.kafka.producedetails.ContactMedium> constructContactMediumList(
            List<ContactMedium> contactMediumList) throws JsonProcessingException {
        List<demo.customermanagement.core.dto.kafka.producedetails.ContactMedium> produceDetailsContactMediumList =
                new ArrayList<>();
        contactMediumList.forEach(contactMedium -> produceDetailsContactMediumList.add(
                demo.customermanagement.core.dto.kafka.producedetails.ContactMedium.newBuilder()
                        .setMediumType(contactMedium.getMediumType())
                        .setCharacteristic(
                                demo.customermanagement.core.dto.kafka.producedetails.Characteristic
                                        .newBuilder()
                                        .setEmailAddress(contactMedium.getCharacteristic().getEmailAddress())
                                        .setPhoneNumber(contactMedium.getCharacteristic().getPhoneNumber())
                                        .build())
                        .build()
        ));
        log.info("List of contact medium to produce to kafka :: {}",
                objectMapper.addMixIn(org.apache.avro.specific.SpecificRecord.class,
                                JacksonIgnoreAvroPropertiesMixIn.class)
                        .writeValueAsString(produceDetailsContactMediumList));
        return produceDetailsContactMediumList;
    }

    public List<demo.customermanagement.core.dto.kafka.producedetails.IndividualIdentification>
    constructIndividualIdentificationList(
            List<IndividualIdentification> individualIdentificationList) throws JsonProcessingException {
        List<demo.customermanagement.core.dto.kafka.producedetails.IndividualIdentification>
                produceDetailsIndividualIdentificationList = new ArrayList<>();
        individualIdentificationList.forEach(
                individualIdentification -> produceDetailsIndividualIdentificationList.add(
                        demo.customermanagement.core.dto.kafka.producedetails.IndividualIdentification
                                .newBuilder()
                                .setIdentificationId(individualIdentification.getIdentificationId())
                                .setIdentificationType(individualIdentification.getIdentificationType())
                                .setIssuingAuthority(individualIdentification.getIssuingAuthority())
                                .setIssuingDate(individualIdentification.getIssuingDate())
                                .setPlaceOfIssue(individualIdentification.getPlaceOfIssue())
                                .setValidFor(demo.customermanagement.core.dto.kafka.producedetails.ValidFor
                                        .newBuilder()
                                        .setStartDateTime(individualIdentification.getValidFor().getStartDateTime())
                                        .setEndDateTime(individualIdentification.getValidFor().getEndDateTime())
                                        .build())
                                .build()
                ));
        log.info("List of individual identification to produce to kafka :: {}",
                objectMapper.addMixIn(org.apache.avro.specific.SpecificRecord.class,
                                JacksonIgnoreAvroPropertiesMixIn.class)
                        .writeValueAsString(produceDetailsIndividualIdentificationList));
        return produceDetailsIndividualIdentificationList;
    }

    public List<demo.customermanagement.core.dto.kafka.producedetails.PartyCharacteristic>
    constructPartyCharacteristicList(List<PartyCharacteristic> partyCharacteristicList)
            throws JsonProcessingException {
        List<demo.customermanagement.core.dto.kafka.producedetails.PartyCharacteristic>
                produceDetailsPartyCharacteristicList = new ArrayList<>();
        partyCharacteristicList.forEach(
                partyCharacteristic -> produceDetailsPartyCharacteristicList.add(
                        demo.customermanagement.core.dto.kafka.producedetails.PartyCharacteristic
                                .newBuilder()
                                .setName(partyCharacteristic.getName())
                                .setValue(partyCharacteristic.getValue())
                                .setValueType(partyCharacteristic.getValueType())
                                .build()
                ));
        log.info("List of party characteristic to produce to kafka :: {}",
                objectMapper.addMixIn(org.apache.avro.specific.SpecificRecord.class,
                                JacksonIgnoreAvroPropertiesMixIn.class)
                        .writeValueAsString(produceDetailsPartyCharacteristicList));
        return produceDetailsPartyCharacteristicList;
    }
}

