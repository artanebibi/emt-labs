package mk.ukim.finki.wp.emt_lab.model.dto;


import lombok.Data;

@Data
public class AuthorDto {
    public String name;
    public String surname;
    public Long country;


    public AuthorDto(String name, String surname, Long country) {
        this.name = name;
        this.surname = surname;
        this.country = country;
    }

    public AuthorDto() {
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Long getCountry() {
        return country;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setCountry(Long country) {
        this.country = country;
    }
}
