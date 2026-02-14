import { Component, OnInit } from '@angular/core';
import { TransactionService } from '../../core/services/transaction.service';
import { Transaction } from '../../models/transaction.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-transactions',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './transactions.component.html',
  styleUrls: ['./transactions.component.css']
})
export class TransactionsComponent implements OnInit {
  transactions: Transaction[] = [];
  filterStatus: string = '';
  error: string | null = null;

  constructor(private transactionService: TransactionService) {}

  ngOnInit() {
    this.fetchTransactions();
  }

  fetchTransactions() {
    this.transactionService.getTransactions(this.filterStatus).subscribe({
      next: (res: any) => {
        this.transactions = res;
      },
      error: () => {
        this.error = 'Failed to load transactions';
      }
    });
  }

  approve(id: string) {
    this.transactionService.approve(id).subscribe(() => this.fetchTransactions());
  }

  reject(id: string) {
    this.transactionService.reject(id).subscribe(() => this.fetchTransactions());
  }
}
