package com.adrian.interview.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@Getter
@Table(name="campaign")
public class RecordModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @Column(name = "data_source")
    String dataSourceType;
    @Column(name = "campaign")
    String campaign;
    @Column(name = "daily")
    LocalDate daily;
    @Column(name = "clicks")
    int clicks;
    @Column(name = "impressions")
    int impressions;

}
