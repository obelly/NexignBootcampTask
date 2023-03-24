package org.example.entity;



import java.util.Objects;

public class Client {
    private Long phoneNumber;
    private TariffType tariffType;

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return phoneNumber.equals(client.phoneNumber) && tariffType == client.tariffType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneNumber, tariffType);
    }

    public Client(Long phoneNumber, TariffType tariffType) {
        this.phoneNumber = phoneNumber;
        this.tariffType = tariffType;
    }

    @Override
    public String toString() {
        return "ClientT{" +
                "phoneNumber=" + phoneNumber +
                ", tariffType=" + tariffType +
                '}';
    }

    public TariffType getTariffType() {
        return tariffType;
    }
}
