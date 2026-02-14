import { Component, OnInit } from '@angular/core';
import { ClerkService } from '../../core/services/clerk.service';
import { Clerk } from '../../models/clerk.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-clerks',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './clerks.component.html',
  styleUrls: ['./clerks.component.css']
})
export class ClerksComponent implements OnInit {
  clerks: Clerk[] = [];
  name = '';
  managerId: number | null = null;
  username = '';
  password = '';
  loading = false;
  error: string | null = null;
  searchEmpId: number | null = null;
  successMessage: string | null = null;
  createdClerk: any | null = null; 
  errorResponse: any | null =null;

  constructor(private clerkService: ClerkService) {}

  ngOnInit() {
    this.fetchClerks();
  }

  fetchClerks() {
    this.clerkService.getAll().subscribe({
      next: (res: any) => this.clerks = res,
      error: () => this.error = 'Failed to fetch clerks'
    });
  }

  createClerk() {
    this.successMessage=null;
    this.createdClerk=null;
    this.error=null;
    this.errorResponse=null;
    if (!this.name || !this.managerId || !this.username || !this.password) {
      this.error = 'Fill all fields';
      return;
    }
    this.error = null;
    this.loading = true;
    this.clerkService.create({
      name: this.name,
      managerId: this.managerId,
      username: this.username,
      password: this.password
    }).subscribe({
      next: (res:any) => {
        this.name = '';
        this.managerId = null;
        this.username = '';
        this.password = '';
        this.fetchClerks();
        this.loading = false;

        this.successMessage = "Clerk Created Successfully";
        this.createdClerk=res;
      },
      error: (err:any) => {
        this.error = 'Failed to create clerk';
        this.loading = false;
        this.errorResponse=err.error;
      }
    });
  }

  deleteClerk(id: number) {
    if (!confirm('Are you sure?')) return;
    this.clerkService.delete(id).subscribe({
      next: () => this.fetchClerks(),
      error: () => alert('Delete failed')
    });
  }

  searchClerk() {
    if (!this.searchEmpId) {
      this.error = 'Enter Employee ID to search';
      return;
    }

    this.error = null;

    this.clerkService.getById(this.searchEmpId).subscribe({
      next: (res: any) => {
        this.clerks = [res];
      },
      error: () => {
        this.error = 'Clerk not found';
        this.clerks = [];
      }
    });
  }

  clearSearch() {
    this.searchEmpId = null;
    this.error = null;
    this.fetchClerks();
  }
}
