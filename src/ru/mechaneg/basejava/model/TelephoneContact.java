package ru.mechaneg.basejava.model;

public class TelephoneContact extends AbstractContact {
    private String telephone;

    public TelephoneContact(String telephone) {
        this.telephone = telephone;
    }

    public String getTelephone() {
        return telephone;
    }

    @Override
    public String toString() {
        return "TelephoneContact{" +
                "telephone='" + telephone + '\'' +
                '}';
    }
}
