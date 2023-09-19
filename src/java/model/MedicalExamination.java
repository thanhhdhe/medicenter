/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author pc
 */
public class MedicalExamination {
    private int MedicalExaminationID;
    private int MuserID;
    private int MchildrenID;
    private int MstaffID;
    private String examinationDate;
    private String usedServices;
    private String medicalPrescription;

    public MedicalExamination() {
    }

    public MedicalExamination(int MedicalExaminationID, int MuserID, int MchildrenID, int MstaffID, String examinationDate, String usedServices, String medicalPrescription) {
        this.MedicalExaminationID = MedicalExaminationID;
        this.MuserID = MuserID;
        this.MchildrenID = MchildrenID;
        this.MstaffID = MstaffID;
        this.examinationDate = examinationDate;
        this.usedServices = usedServices;
        this.medicalPrescription = medicalPrescription;
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

    public String getExaminationDate() {
        return examinationDate;
    }

    public void setExaminationDate(String examinationDate) {
        this.examinationDate = examinationDate;
    }

    public String getUsedServices() {
        return usedServices;
    }

    public void setUsedServices(String usedServices) {
        this.usedServices = usedServices;
    }

    public String getMedicalPrescription() {
        return medicalPrescription;
    }

    public void setMedicalPrescription(String medicalPrescription) {
        this.medicalPrescription = medicalPrescription;
    }
    
}
