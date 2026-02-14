import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';
import { LoginComponent } from './pages/login/login.component';
import { HomeComponent } from './pages/home/home.component';
import { AccountsComponent } from './pages/accounts/accounts.component';
import { ClerksComponent } from './pages/clerks/clerks.component';
import { TransactionsComponent } from './pages/transactions/transactions.component';
import { OperationsComponent } from './pages/operations/operations.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },

  {
    path: '',
    component: HomeComponent,
    canActivate: [authGuard],
    children: [
      { path: 'accounts', component: AccountsComponent },
      { path: 'clerks', component: ClerksComponent },
      { path: 'transactions', component: TransactionsComponent },
      { path: 'operations', component: OperationsComponent }
    ]
  },

  { path: '**', redirectTo: '' }
];
