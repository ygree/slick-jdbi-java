package com.example.hello;

import lombok.Value;

/**
 * NOTE: @Value is a Lombok annotation and it requires to enable Java annotation processing
 */
@Value
public class Contact {
    String name;
}
