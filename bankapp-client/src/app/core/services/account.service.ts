import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Account } from '../../models/account.model';

@Injectable({ providedIn: 'root' })
export class AccountService {

  constructor(private http: HttpClient) {}

  getAll() {
    return this.http.get<Account>(`${environment.baseUrl}/accounts`);
  }

  create(data: Partial<Account>) {
    return this.http.post<Account>(`${environment.baseUrl}/accounts`, data);
  }

  delete(id: string) {
    return this.http.delete(`${environment.baseUrl}/accounts/${id}`);
  }

  getById(id: string) {
    return this.http.get<Account>(`${environment.baseUrl}/accounts/${id}`);
  }
}
