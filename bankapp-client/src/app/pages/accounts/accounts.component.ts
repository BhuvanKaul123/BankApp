import { Component, OnInit } from '@angular/core';
import { AccountService } from '../../core/services/account.service';
import { Account } from '../../models/account.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-accounts',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './accounts.component.html',
  styleUrls: ['./accounts.component.css']
})
export class AccountsComponent implements OnInit {
  accounts: Account[] = [];
  holderName = '';
  balance: number | null = null;
  loading = false;
  createError: string | null = null;
  searchId = '';
  successMessage: string | null = null;
  createdAccount: Account | null = null;
  creationErrorJson: any | null = null;

  constructor(private accountService: AccountService) {}

  ngOnInit() {
    this.fetchAccounts();
  }

  fetchAccounts() {
    this.accountService.getAll().subscribe({
      next: (res: any) => {
        this.accounts = res;
      },
      error: () => {
        this.createError = 'Failed to fetch accounts';
      }
    });
  }

  createAccount() {
    this.successMessage = null;
    this.createdAccount = null;
    if (!this.holderName || this.balance === null || this.balance < 0) {
      this.createError = 'Please enter valid name and balance';
      return;
    }
    this.createError = null;
    this.loading = true;
    this.accountService.create({ holderName: this.holderName, balance: this.balance }).subscribe({
      next: (res : Account) => {
        this.successMessage = 'Account created successfully!';
        this.createdAccount = res;
        this.holderName = '';
        this.balance = null;
        this.fetchAccounts();
        this.loading = false;
        
      },
      error: (err: any) => {
        this.createError = 'Failed to create account';
        this.loading = false;
        this.creationErrorJson=err.error;
      }
    });
  }

  deleteAccount(id: string) {
    if (!confirm('Are you sure you want to delete this account?')) return;
    this.accountService.delete(id).subscribe({
      next: () => this.fetchAccounts(),
      error: () => alert('Delete failed')
    });
  }

searchAccount() {
  if (!this.searchId.trim()) {
    this.fetchAccounts();
    return;
  }

  this.accountService.getById(this.searchId.trim()).subscribe({
    next: (res: any) => {
      this.accounts = [res];
    },
    error: () => {
      this.accounts = [];
    }
  });
}

clearSearch() {
  this.searchId = '';
  this.fetchAccounts();
}

}
