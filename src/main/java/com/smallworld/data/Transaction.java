package com.smallworld.data;

public class Transaction {
    // Represent your transaction data here.
    private int mtn;
    private double amount;
    private String senderFullName;
    private int senderAge;
    private String beneficiaryFullName;
    private int beneficiaryAge;
    private Integer issueId;
    private boolean issueSolved;
    private String issueMessage;

    //adding only getters

    public int getMtn() {
        return mtn;
    }

    public double getAmount() {
        return amount;
    }

    public String getSenderFullName() {
        return senderFullName;
    }

    public int getSenderAge() {
        return senderAge;
    }

    public String getBeneficiaryFullName() {
        return beneficiaryFullName;
    }

    public int getBeneficiaryAge() {
        return beneficiaryAge;
    }

    public Integer getIssueId() {
        return issueId;
    }

    public boolean isIssueSolved() {
        return issueSolved;
    }

    public String getIssueMessage() {
        return issueMessage;
    }

    @Override
    public String toString() {
        return "Transaction mtn: " + mtn +
                ", amount: " + amount +
                ", senderFullName: " + senderFullName +
                ", senderAge: " + senderAge +
                ", beneficiaryFullName: " + beneficiaryFullName +
                ", beneficiaryAge: " + beneficiaryAge +
                ", issueId: " + issueId +
                ", issueSolved: " + issueSolved +
                ", IssueMessage: " + issueMessage;
    }
}
