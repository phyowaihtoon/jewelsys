package com.devgroup.jewelsys;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.devgroup.jewelsys");

        noClasses()
            .that()
            .resideInAnyPackage("com.devgroup.jewelsys.service..")
            .or()
            .resideInAnyPackage("com.devgroup.jewelsys.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.devgroup.jewelsys.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
