package team.projectpulse.student;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import team.projectpulse.instructor.Instructor;
import team.projectpulse.section.Section;
import team.projectpulse.system.UserUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class StudentSecurityServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private UserUtils userUtils;

    @InjectMocks
    private StudentSecurityService studentSecurityService;

    @Test
    void returnsFalseWhenStudentDoesNotExist() {
        given(this.userUtils.getUserId()).willReturn(42);
        given(this.studentRepository.findById(7)).willReturn(Optional.empty());

        assertThat(this.studentSecurityService.isCurrentUserInstructorOfStudentSection(7)).isFalse();
    }

    @Test
    void returnsTrueWhenCurrentInstructorTeachesStudentsSection() {
        Instructor instructor = new Instructor();
        instructor.setId(42);
        Section section = new Section();
        section.addInstructor(instructor);
        Student student = new Student();
        student.setSection(section);

        given(this.userUtils.getUserId()).willReturn(42);
        given(this.studentRepository.findById(7)).willReturn(Optional.of(student));

        assertThat(this.studentSecurityService.isCurrentUserInstructorOfStudentSection(7)).isTrue();
    }
}
