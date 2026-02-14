import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Transaction } from '../../models/transaction.model';

@Injectable({ providedIn: 'root' })
export class TransactionService {

  constructor(private http: HttpClient) {}

  deposit(accountId: string, amount: number) {
    return this.http.post<Transaction>(
      `${environment.baseUrl}/api/accounts/${accountId}/deposits`,
      { amount }
    );
  }

  withdraw(accountId: string, amount: number) {
    return this.http.post<Transaction>(
      `${environment.baseUrl}/api/accounts/${accountId}/withdrawals`,
      { amount }
    );
  }

  transfer(fromId: string, toId: string, amount: number) {
    return this.http.post<Transaction>(
      `${environment.baseUrl}/api/accounts/${fromId}/transfers`,
      { toAccountIdString: toId, amount }
    );
  }

  getTransactions(status?: string) {
    return this.http.get<Transaction>(
      `${environment.baseUrl}/api/transactions?status=${status || ''}`
    );
  }

  approve(id: string) {
    return this.http.patch(
      `${environment.baseUrl}/api/transactions/${id}/approve`, {}
    );
  }

  reject(id: string) {
    return this.http.patch(
      `${environment.baseUrl}/api/transactions/${id}/reject`, {}
    );
  }
}
