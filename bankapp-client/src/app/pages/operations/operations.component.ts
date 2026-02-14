import { Component } from '@angular/core';
import { TransactionService } from '../../core/services/transaction.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Transaction } from '../../models/transaction.model';

@Component({
  selector: 'app-operations',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './operations.component.html',
  styleUrls: ['./operations.component.css']
})
export class OperationsComponent {
  depositAccountId = '';
  depositAmount: number | null = null;

  withdrawAccountId = '';
  withdrawAmount: number | null = null;

  transferFromId = '';
  transferToId = '';
  transferAmount: number | null = null;

  depositMessage: string | null = null;
  withdrawMessage: string | null = null;
  transferMessage: string | null = null;

  depositSuccess = false;
  depositError: any | null = null;
  depositTransaction: any | null = null;

  withdrawSuccess = false;
  withdrawError: any | null = null;
  withdrawTransaction: any | null = null;

  transferSuccess = false;
  transferError: any | null = null;
  transferTransaction: any | null = null;

  constructor(private transactionService: TransactionService) {}

  deposit() {
    this.clearMessages();
    if (!this.depositAccountId || !this.depositAmount || this.depositAmount <= 0) {
      this.depositMessage = 'Enter valid deposit account and amount';
      this.depositSuccess=false;
      return;
    }
    this.transactionService.deposit(this.depositAccountId, this.depositAmount).subscribe({
      next: (res:any) => {
        this.depositMessage = 'Deposit successful';
        this.depositAccountId = '';
        this.depositAmount = null;
        this.depositSuccess=true;
        this.depositTransaction = res;
      },
      error: (err:any) => {
        this.depositMessage = 'Deposit failed';
        this.depositSuccess = false;
        this.depositError = err.error;
      }
    });
  }

  withdraw() {
    this.clearMessages();
    if (!this.withdrawAccountId || !this.withdrawAmount || this.withdrawAmount <= 0) {
      this.withdrawMessage = 'Enter valid withdrawal account and amount';
      this.withdrawSuccess=false;
      return;
    }
    this.transactionService.withdraw(this.withdrawAccountId, this.withdrawAmount).subscribe({
      next: (res:any) => {
        this.withdrawMessage = 'Withdrawal successful';
        this.withdrawAccountId = '';
        this.withdrawAmount = null;
        this.withdrawSuccess=true;
        this.withdrawTransaction=res;
      },
      error: (err : any) => {
        this.withdrawMessage = 'Withdrawal failed';
        this.withdrawSuccess=false;
        this.withdrawError=err.error;
      }
    });
  }

  transfer() {
    this.clearMessages();
    if (
      !this.transferFromId ||
      !this.transferToId ||
      !this.transferAmount ||
      this.transferAmount <= 0
    ) {
      this.transferMessage = 'Enter valid transfer details';
      this.transferSuccess=false;
      return;
    }
    this.transactionService.transfer(this.transferFromId, this.transferToId, this.transferAmount).subscribe({
      next: (res : any) => {
        this.transferMessage = 'Transfer successful';
        this.transferFromId = '';
        this.transferToId = '';
        this.transferAmount = null;
        this.transferSuccess=true;
        this.transferTransaction = res;
      },
      error: (err: any) => {
        this.transferMessage = 'Transfer failed';
        this.transferSuccess=false;
        this.transferError = err.error;
      }
    });
  }

  clearMessages() {
    this.depositMessage = null;
    this.withdrawMessage = null;
    this.transferMessage = null;

    this.depositError=null;
    this.depositTransaction=null;
    this.depositSuccess=false;

    this.withdrawError=null;
    this.withdrawTransaction=null;
    this.withdrawSuccess=false;

    this.transferError=null;
    this.transferTransaction=null;
    this.transferSuccess=false;
  }

}
