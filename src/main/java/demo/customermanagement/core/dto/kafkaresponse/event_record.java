/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package demo.customermanagement.core.dto.kafkaresponse;

import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.SchemaStore;
import org.apache.avro.specific.SpecificData;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class event_record extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 1112206149584424084L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"event_record\",\"namespace\":\"demo.customermanagement.core.dto.kafkaresponse\",\"fields\":[{\"name\":\"customer\",\"type\":{\"type\":\"record\",\"name\":\"customer_record\",\"fields\":[{\"name\":\"at_type\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"href\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"id\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"name\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"status\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"statusReason\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"validFor\",\"type\":{\"type\":\"record\",\"name\":\"validFor\",\"fields\":[{\"name\":\"startDateTime\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"endDateTime\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}]}},{\"name\":\"engagedParty\",\"type\":{\"type\":\"record\",\"name\":\"engagedParty_record\",\"fields\":[{\"name\":\"at_referredType\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"givenName\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"middleName\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"familyName\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"fullName\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"gender\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"birthDate\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"nationality\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"individualIdentification\",\"type\":{\"type\":\"record\",\"name\":\"individualIdentification_record\",\"fields\":[{\"name\":\"identificationId\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"identificationType\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"issuingDate\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"issuingAuthority\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"placeOfIssue\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"validFor\",\"type\":{\"type\":\"record\",\"name\":\"validFor_record\",\"fields\":[{\"name\":\"startDateTime\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"endDateTime\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null}]}}]}},{\"name\":\"contactMedium\",\"type\":{\"type\":\"record\",\"name\":\"contactMedium_record\",\"fields\":[{\"name\":\"mediumType\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"characteristic\",\"type\":{\"type\":\"record\",\"name\":\"characteristic_record\",\"fields\":[{\"name\":\"emailAddress\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"phoneNumber\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null}]}}]}},{\"name\":\"partyCharacteristic\",\"type\":{\"type\":\"record\",\"name\":\"partyCharacteristic_record\",\"fields\":[{\"name\":\"name\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"value\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"valueType\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null}]}}]}}]}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<event_record> ENCODER =
      new BinaryMessageEncoder<event_record>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<event_record> DECODER =
      new BinaryMessageDecoder<event_record>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   */
  public static BinaryMessageDecoder<event_record> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   */
  public static BinaryMessageDecoder<event_record> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<event_record>(MODEL$, SCHEMA$, resolver);
  }

  /** Serializes this event_record to a ByteBuffer. */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /** Deserializes a event_record from a ByteBuffer. */
  public static event_record fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

   private customer_record customer;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public event_record() {}

  /**
   * All-args constructor.
   * @param customer The new value for customer
   */
  public event_record(customer_record customer) {
    this.customer = customer;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return customer;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: customer = (customer_record)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'customer' field.
   * @return The value of the 'customer' field.
   */
  public customer_record getCustomer() {
    return customer;
  }


  /**
   * Creates a new event_record RecordBuilder.
   * @return A new event_record RecordBuilder
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  /**
   * Creates a new event_record RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new event_record RecordBuilder
   */
  public static Builder newBuilder(Builder other) {
    return new Builder(other);
  }

  /**
   * Creates a new event_record RecordBuilder by copying an existing event_record instance.
   * @param other The existing instance to copy.
   * @return A new event_record RecordBuilder
   */
  public static Builder newBuilder(event_record other) {
    return new Builder(other);
  }

  /**
   * RecordBuilder for event_record instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<event_record>
    implements org.apache.avro.data.RecordBuilder<event_record> {

    private customer_record customer;
    private customer_record.Builder customerBuilder;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.customer)) {
        this.customer = data().deepCopy(fields()[0].schema(), other.customer);
        fieldSetFlags()[0] = true;
      }
      if (other.hasCustomerBuilder()) {
        this.customerBuilder = customer_record.newBuilder(other.getCustomerBuilder());
      }
    }

    /**
     * Creates a Builder by copying an existing event_record instance
     * @param other The existing instance to copy.
     */
    private Builder(event_record other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.customer)) {
        this.customer = data().deepCopy(fields()[0].schema(), other.customer);
        fieldSetFlags()[0] = true;
      }
      this.customerBuilder = null;
    }

    /**
      * Gets the value of the 'customer' field.
      * @return The value.
      */
    public customer_record getCustomer() {
      return customer;
    }

    /**
      * Sets the value of the 'customer' field.
      * @param value The value of 'customer'.
      * @return This builder.
      */
    public Builder setCustomer(customer_record value) {
      validate(fields()[0], value);
      this.customerBuilder = null;
      this.customer = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'customer' field has been set.
      * @return True if the 'customer' field has been set, false otherwise.
      */
    public boolean hasCustomer() {
      return fieldSetFlags()[0];
    }

    /**
     * Gets the Builder instance for the 'customer' field and creates one if it doesn't exist yet.
     * @return This builder.
     */
    public customer_record.Builder getCustomerBuilder() {
      if (customerBuilder == null) {
        if (hasCustomer()) {
          setCustomerBuilder(customer_record.newBuilder(customer));
        } else {
          setCustomerBuilder(customer_record.newBuilder());
        }
      }
      return customerBuilder;
    }

    /**
     * Sets the Builder instance for the 'customer' field
     * @param value The builder instance that must be set.
     * @return This builder.
     */
    public Builder setCustomerBuilder(customer_record.Builder value) {
      clearCustomer();
      customerBuilder = value;
      return this;
    }

    /**
     * Checks whether the 'customer' field has an active Builder instance
     * @return True if the 'customer' field has an active Builder instance
     */
    public boolean hasCustomerBuilder() {
      return customerBuilder != null;
    }

    /**
      * Clears the value of the 'customer' field.
      * @return This builder.
      */
    public Builder clearCustomer() {
      customer = null;
      customerBuilder = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public event_record build() {
      try {
        event_record record = new event_record();
        if (customerBuilder != null) {
          record.customer = this.customerBuilder.build();
        } else {
          record.customer = fieldSetFlags()[0] ? this.customer : (customer_record) defaultValue(fields()[0]);
        }
        return record;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<event_record>
    WRITER$ = (org.apache.avro.io.DatumWriter<event_record>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<event_record>
    READER$ = (org.apache.avro.io.DatumReader<event_record>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}
