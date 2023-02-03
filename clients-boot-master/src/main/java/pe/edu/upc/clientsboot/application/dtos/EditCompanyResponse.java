package pe.edu.upc.clientsboot.application.dtos;

import lombok.Value;

@Value
public class EditCompanyResponse {
    private String id;
    private String ruc;
    private String name;
    private String address;
    private String email;
    private String phone;
}