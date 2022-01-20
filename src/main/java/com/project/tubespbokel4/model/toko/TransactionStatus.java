package com.project.tubespbokel4.model.toko;

/**
 * Class Enum WalletLogStatus berfungsi untuk menghindari isian yang tidak dinginkan terhadap kolom status pada @Object <B>Class Transaction</B>.
 */
public enum TransactionStatus {
    member_request,
    member_cancel,
    seller_approved,
    seller_rejected,
    done
}
