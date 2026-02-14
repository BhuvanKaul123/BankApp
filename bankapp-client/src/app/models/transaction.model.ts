export interface Transaction {
  transactionId: string;
  transactionType: string;
  transactionStatus: string;
  failureReason: string;
  initiatorAccountId: string;
  recieverAccountId: string;
  amount: number;
}
