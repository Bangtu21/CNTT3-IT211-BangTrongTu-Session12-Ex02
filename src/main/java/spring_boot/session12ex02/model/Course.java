package spring_boot.session12ex02.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Course {
    private Long id;
    private String courseName;
    private String instructor;
    private Integer durationHours;
    private Double fee;
}
