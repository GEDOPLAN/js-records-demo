package de.gedoplan.showcase.jv;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record Person(
    @NotNull @Size(min = 2) String name,
    @PastOrPresent LocalDate birthday,
    @Valid Address address) {
}
