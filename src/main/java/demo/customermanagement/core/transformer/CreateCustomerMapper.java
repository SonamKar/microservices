package demo.customermanagement.core.transformer;

import demo.customermanagement.core.dto.kafkaresponse.event_record;
import demo.customermanagement.core.dto.request.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
public class CreateCustomerMapper {
    public Customer_Create constructCustomerRequest(final event_record eventRecord){
        return Customer_Create.builder()
                .name(eventRecord.getCustomer().getName())
                .engagedParty(RelatedParty.builder()
                        .givenName(eventRecord.getCustomer().getEngagedParty().getGivenName())
                        .birthDate(eventRecord.getCustomer().getEngagedParty().getBirthDate())
                        .contactMedium(Arrays.asList(
                                ContactMedium.builder()
                                        .characteristic(MediumCharacteristic.builder()
                                                .emailAddress(eventRecord.getCustomer().getEngagedParty()
                                                        .getContactMedium().getCharacteristic().getEmailAddress())
                                                .phoneNumber(eventRecord.getCustomer().getEngagedParty()
                                                                .getContactMedium().getCharacteristic().getPhoneNumber())
                                                .build())
                                        .mediumType(eventRecord.getCustomer().getEngagedParty().getContactMedium().getMediumType())
                                        .build()))
                        .familyName(eventRecord.getCustomer().getEngagedParty().getFamilyName())
                        .fullName(eventRecord.getCustomer().getEngagedParty().getFullName())
                        .gender(eventRecord.getCustomer().getEngagedParty().getGender())
                        .middleName(eventRecord.getCustomer().getEngagedParty().getMiddleName())
                        .nationality(eventRecord.getCustomer().getEngagedParty().getNationality())
                        .partyCharacteristic(Arrays.asList(
                                Characteristic.builder()
                                        .name(eventRecord.getCustomer().getEngagedParty().getPartyCharacteristic().getName())
                                        .value(eventRecord.getCustomer().getEngagedParty().getPartyCharacteristic().getValue())
                                        .valueType(eventRecord.getCustomer().getEngagedParty().getPartyCharacteristic().getValueType())
                                        .build()))
                        .individualIdentification(Arrays.asList(
                                IndividualIdentification.builder()
                                        .identificationId(eventRecord.getCustomer().getEngagedParty().getIndividualIdentification()
                                                .getIdentificationId())
                                        .identificationType(eventRecord.getCustomer().getEngagedParty().getIndividualIdentification()
                                                .getIdentificationType())
                                        .issuingAuthority(eventRecord.getCustomer().getEngagedParty().getIndividualIdentification()
                                                .getIssuingAuthority())
                                        .issuingDate(eventRecord.getCustomer().getEngagedParty().getIndividualIdentification()
                                                .getIssuingDate())
                                        .placeOfIssue(eventRecord.getCustomer().getEngagedParty().getIndividualIdentification()
                                                .getPlaceOfIssue())
                                        .validFor(TimePeriod.builder()
                                                .startDateTime(eventRecord.getCustomer().getEngagedParty().getIndividualIdentification()
                                                        .getValidFor().getStartDateTime())
                                                .endDateTime(eventRecord.getCustomer().getEngagedParty().getIndividualIdentification()
                                                        .getValidFor().getEndDateTime())
                                                .build())
                                        .build()))
                        .build())
                .build();
    }
}
