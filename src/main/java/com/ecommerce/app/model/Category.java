package com.ecommerce.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

//both is correct way, one give table name based on class name
//@Entity
@Entity(name="categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

//    public Category(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    // it will give 500 eror internal server error, to handle this use valid annotation
    //in combination with NotBlank, so it will givee 400 bad request error
    //because using @Valid spring automatically understand and checks the request, so it will check that categpry should not blank
            //so it will throw 400 bad request error, instead of throwing internal server error.
    //if suppose we comment out NotBlank then as per valid it is correct request, becuase no contraint is mention
    @NotBlank
    @Size(min=5, message = "Category name must contain at least 5 characters")
//    @Size(min=5) it will print default msg
    private String categoryName;

}
//    public Long getCategoryId() {
//        return categoryId;
//    }
//
//    public void setCategoryId(Long categoryId) {
//        this.categoryId = categoryId;
//    }
//
//    public String getCategoryName() {
//        return categoryName;
//    }
//
//    public void setCategoryName(String categoryName) {
//        this.categoryName = categoryName;
//    }


//        @GeneratedValue(strategy = GenerationType.IDENTITY)
//- Purpose: It marks a field (usually the id) as the primary key and specifies how its value should be generated.
//        - GenerationType.IDENTITY:
//        - Uses the identity column feature of the database.
//        - The database itself generates the primary key value when a new row is inserted (commonly via AUTO_INCREMENT in MySQL or SERIAL in PostgreSQL).
//        - Hibernate doesn’t pre-generate the ID; instead, it waits until the insert happens and then retrieves the generated value.
//Types of @GeneratedValue
//- GenerationType.IDENTITY
//- Uses the database’s auto-increment/identity column.
//        - ID is generated when a row is inserted.
//        - Common in MySQL, SQL Server, PostgreSQL (SERIAL).
//        - GenerationType.SEQUENCE
//- Uses a database sequence object to generate IDs.
//        - Hibernate can fetch IDs before insert → better for batch inserts.
//        - Common in Oracle, PostgreSQL.
//- GenerationType.TABLE
//- Uses a separate table to store and generate IDs.
//- More portable but less efficient.
//- Rarely used in modern apps.
//- GenerationType.AUTO
//- Hibernate chooses the strategy based on the database dialect.
//- Convenient, but less predictable across different databases.
//
//        ⚙️ Example
//        @Id
//        @GeneratedValue(strategy = GenerationType.SEQUENCE)
//        private Long id;
//
//
//
//👉 In short:
//        - IDENTITY → simple, DB auto-increment.
//- SEQUENCE → efficient, uses DB sequences.
//- TABLE → legacy, uses a table for IDs.
//- AUTO → lets Hibernate decide.
