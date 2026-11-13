package team.projectpulse.student;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StudentSpecsTest {

    @Test
    @SuppressWarnings("unchecked")
    void lowercasesSearchTextIndependentOfDefaultLocale() {
        Locale originalLocale = Locale.getDefault();
        try {
            Locale.setDefault(Locale.forLanguageTag("tr-TR"));
            Root<Student> root = mock(Root.class);
            CriteriaQuery<?> query = mock(CriteriaQuery.class);
            CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
            Path<String> firstName = mock(Path.class);
            Expression<String> loweredFirstName = mock(Expression.class);
            Predicate predicate = mock(Predicate.class);

            when(root.<String>get("firstName")).thenReturn(firstName);
            when(criteriaBuilder.lower(firstName)).thenReturn(loweredFirstName);
            when(criteriaBuilder.like(loweredFirstName, "%ipek%")).thenReturn(predicate);

            assertThat(StudentSpecs.containsFirstName("IPEK").toPredicate(root, query, criteriaBuilder))
                    .isSameAs(predicate);
        } finally {
            Locale.setDefault(originalLocale);
        }
    }
}
