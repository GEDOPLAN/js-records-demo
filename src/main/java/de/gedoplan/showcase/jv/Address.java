package de.gedoplan.showcase.jv;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Address {
  @NotNull
  private String street;
  @NotNull
  private String city;
  @NotNull
  @Pattern(regexp = "\\d{5}")
  private String postcode;

  public Address(String street, String city, String postcode) {
    this.street = street;
    this.city = city;
    this.postcode = postcode;
  }
}
