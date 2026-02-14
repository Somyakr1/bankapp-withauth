import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import {
  AccountRequest,
  ApprovalRequest,
  DepositRequest,
  TransferRequest,
  WithdrawRequest
} from '../models/bank.models';

@Injectable({ providedIn: 'root' })
export class ApiService {
  private readonly base = environment.apiBaseUrl;

  constructor(private http: HttpClient) {}

  createClerk(payload: { username: string; password: string }) { return this.http.post(`${this.base}/v2/users/create-clerk`, payload); }

  addAccount(payload: AccountRequest) { return this.http.post(`${this.base}/v2/accounts/add`, payload); }
  deleteAccount(id: number) { return this.http.delete(`${this.base}/v2/accounts/delete/${id}`); }
  getAllAccounts() { return this.http.get(`${this.base}/v2/accounts/accounts`); }
  getAccountById(id: number) { return this.http.get(`${this.base}/v2/accounts/accounts/${id}`); }
  getAccountWithTransactions(id: number) { return this.http.get(`${this.base}/v2/accounts/${id}/with-transactions`); }
  getAccountSummary() { return this.http.get(`${this.base}/v2/accounts/summary`); }
  getAccountSummaryByAccount(id: number) { return this.http.get(`${this.base}/v2/accounts/summary/by-account-number/${id}`); }

  transfer(payload: TransferRequest) { return this.http.put(`${this.base}/v2/transactions/transfer`, payload); }
  deposit(payload: DepositRequest) { return this.http.put(`${this.base}/v2/transactions/deposit`, payload); }
  withdraw(payload: WithdrawRequest) { return this.http.put(`${this.base}/v2/transactions/withdraw`, payload); }
  getAllTransactions() { return this.http.get(`${this.base}/v2/transactions`); }
  getTransactionById(id: number) { return this.http.get(`${this.base}/v2/transactions/${id}`); }
  getTransactionCount(id: number) { return this.http.get(`${this.base}/v2/transactions/account/${id}/count`); }
  approveWithdrawal(payload: ApprovalRequest) { return this.http.put(`${this.base}/v2/transactions/approve-withdrawal`, payload); }
}
