package com.logic.wordreader.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constant {
    public static Map<String, String> names = new HashMap<String, String>();
    public static List<String> top = new ArrayList<String>();

    static {
        top.add("SONRQ");
        top.add("SONRS");
        top.add("SIGNUPMSGSV1");
        top.add("ENROLLTRN");
        top.add("ENROLLINQTRN");
        top.add("CHGUSERINFOTRN");
        top.add("ACCTSYNC");
        top.add("ACCTTRN");
        top.add("ACCTINFOTRN");
        top.add("BILLPAYMSGSV1");
        top.add("PAYEESYNC");
        top.add("PAYEETRN");
        top.add("PMTTRN");
        top.add("PMTSYNC");
        top.add("PMTINQTRN");
        top.add("RECPMTTRN");
        top.add("RECPMTSYNC");
        top.add("PMTMAILTRN");
        top.add("PMTMAILSYNC");
        top.add("GLOBALPMTSYNC");
        top.add("PROFMSGS");
        top.add("PROFTRN");
    }

    static {
        names.put("FI", "Fi");
        names.put("RQ", "Rq");
        names.put("MSGS", "Msgs");
        names.put("SON", "Son");
        names.put("SIGNON", "Signon");
        names.put("SIGNUP", "Signup");
        names.put("ENROLL", "Enroll");
        names.put("TRN", "Trn");
        names.put("USERREF", "UserRef");
        names.put("PAYEE", "Payee");
        names.put("DEL", "Del");
        names.put("ADD", "Add");
        names.put("MOD", "Mod");
        names.put("CANC", "Canc");
        names.put("SYNC", "Sync");
        names.put("BILLPAY", "BillPay");
        names.put("CATEGORY", "Category");
        names.put("BANK", "Bank");
        names.put("ACCT", "Acct");
        names.put("FROM", "From");
        names.put("PMT", "Pmt");
        names.put("INFO", "Info");
        names.put("CHALLENGE", "Challenge");
        names.put("SUMMARY", "Summary");
        names.put("EXTD", "Extd");
        names.put("INQ", "Inq");
        names.put("REC", "Rec");
        names.put("URR", "urr");
        names.put("MAIL", "Mail");
        names.put("GLOBAL", "Global");
        names.put("INST", "Inst");
        names.put("INCTRAN", "IncTran");
        names.put("CHG", "Chg");
        names.put("USER", "User");
        names.put("PROF", "Prof");
        names.put("SVC", "Svc");
        names.put("Add", "Add");
        names.put("BUSINESS", "Business");
        names.put("STATUS", "Status");
        names.put("RS", "Rs");
        names.put("USERDATA", "UserData");
        names.put("ACCTTO", "AcctTo");
        names.put("BP", "Bp");
        names.put("PRES", "Pres");
        names.put("PRCSTS", "PrsSts");
    }


}
