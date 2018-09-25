package seedu.address.testutil;

import seedu.address.model.PredictiveTranscript;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private PredictiveTranscript addressBook;

    public AddressBookBuilder() {
        addressBook = new PredictiveTranscript();
    }

    public AddressBookBuilder(PredictiveTranscript addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withPerson(Person person) {
        addressBook.addPerson(person);
        return this;
    }

    public PredictiveTranscript build() {
        return addressBook;
    }
}
