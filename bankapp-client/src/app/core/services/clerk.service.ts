import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Clerk } from '../../models/clerk.model';

@Injectable({ providedIn: 'root' })
export class ClerkService {

  constructor(private http: HttpClient) {}

  getAll() {
    return this.http.get<Clerk>(`${environment.baseUrl}/clerks`);
  }

  create(data: any) {
    return this.http.post(`${environment.baseUrl}/clerks`, data);
  }

  delete(id: number) {
    return this.http.delete(`${environment.baseUrl}/clerks/${id}`);
  }

    getById(id: number) {
    return this.http.get<Clerk>(`${environment.baseUrl}/clerks/${id}`);
  }

}
