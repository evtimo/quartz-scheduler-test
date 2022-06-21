package com.example.test.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "currency")
public class Curr extends BaseEntity{

	private String name;
}
