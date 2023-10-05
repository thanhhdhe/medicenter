/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author pc
 */
public class MedicalExamination {
    private int MedicalExaminationID;
    private int MuserID;
    private int MchildrenID;
    private int MstaffID;
    private Date examinationDate;
    private int  serviceID;
    private String medicalPrescription;
    private String disease;

    public MedicalExamination() {
    }

    public MedicalExamination(int MedicalExaminationID, int MuserID, int MchildrenID, int MstaffID, Date examinationDate, int serviceID, String medicalPrescription) {
        this.MedicalExaminationID = MedicalExaminationID;
        this.MuserID = MuserID;
        this.MchildrenID = MchildrenID;
        this.MstaffID = MstaffID;
        this.examinationDate = examinationDate;
        this.serviceID = serviceID;
        this.medicalPrescription = medicalPrescription;
    }
    
    public MedicalExamination(int MedicalExaminationID, int MuserID, int MchildrenID, int MstaffID, Date examinationDate, int serviceID, String medicalPrescription, String disease) {
        this.MedicalExaminationID = MedicalExaminationID;
        this.MuserID = MuserID;
        this.MchildrenID = MchildrenID;
        this.MstaffID = MstaffID;
        this.examinationDate = examinationDate;
        this.serviceID = serviceID;
        this.medicalPrescription = medicalPrescription;
        this.disease = disease;
    }

    public MedicalExamination(int MuserID, int MchildrenID, int MstaffID, Date examinationDate, int serviceID, String medicalPrescription, String disease) {
        this.MuserID = MuserID;
        this.MchildrenID = MchildrenID;
        this.MstaffID = MstaffID;
        this.examinationDate = examinationDate;
        this.serviceID = serviceID;
        this.medicalPrescription = medicalPrescription;
        this.disease = disease;
    }

    

    public int getMedicalExaminationID() {
        return MedicalExaminationID;
    }

    public void setMedicalExaminationID(int MedicalExaminationID) {
        this.MedicalExaminationID = MedicalExaminationID;
    }

    public int getMuserID() {
        return MuserID;
    }

    public void setMuserID(int MuserID) {
        this.MuserID = MuserID;
    }

    public int getMchildrenID() {
        return MchildrenID;
    }

    public void setMchildrenID(int MchildrenID) {
        this.MchildrenID = MchildrenID;
    }

    public int getMstaffID() {
        return MstaffID;
    }

    public void setMstaffID(int MstaffID) {
        this.MstaffID = MstaffID;
    }

    public Date getExaminationDate() {
        return examinationDate;
    }

    public void setExaminationDate(Date examinationDate) {
        this.examinationDate = examinationDate;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public String getMedicalPrescription() {
        return medicalPrescription;
    }

    public void setMedicalPrescription(String medicalPrescription) {
        this.medicalPrescription = medicalPrescription;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }
    
}
