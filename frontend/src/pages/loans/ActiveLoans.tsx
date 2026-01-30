import { useState } from 'react'
import { Search, Eye, TrendingUp, Calendar, DollarSign } from 'lucide-react'

interface ActiveLoan {
  id: number
  borrowerName: string
  nic: string
  loanAmount: number
  outstandingBalance: number
  monthlyPayment: number
  interestRate: number
  startDate: string
  endDate: string
  nextPaymentDate: string
  status: 'on_track' | 'overdue' | 'at_risk'
}

const mockLoans: ActiveLoan[] = [
  { id: 1, borrowerName: 'John Perera', nic: '199012345678', loanAmount: 500000, outstandingBalance: 420000, monthlyPayment: 15000, interestRate: 12.5, startDate: '2025-06-01', endDate: '2028-06-01', nextPaymentDate: '2026-02-01', status: 'on_track' },
  { id: 2, borrowerName: 'Mary Silva', nic: '198523456789', loanAmount: 250000, outstandingBalance: 180000, monthlyPayment: 8500, interestRate: 10.0, startDate: '2025-03-15', endDate: '2027-03-15', nextPaymentDate: '2026-02-15', status: 'on_track' },
  { id: 3, borrowerName: 'Kumar Fernando', nic: '199234567890', loanAmount: 1000000, outstandingBalance: 950000, monthlyPayment: 35000, interestRate: 14.0, startDate: '2025-12-01', endDate: '2030-12-01', nextPaymentDate: '2026-01-25', status: 'overdue' },
  { id: 4, borrowerName: 'Nimal Jayasinghe', nic: '198745678901', loanAmount: 750000, outstandingBalance: 600000, monthlyPayment: 22000, interestRate: 11.5, startDate: '2025-08-01', endDate: '2029-08-01', nextPaymentDate: '2026-02-01', status: 'at_risk' },
]

const statusConfig = {
  on_track: { label: 'On Track', color: 'bg-green-100 text-green-800' },
  overdue: { label: 'Overdue', color: 'bg-red-100 text-red-800' },
  at_risk: { label: 'At Risk', color: 'bg-yellow-100 text-yellow-800' },
}

export default function ActiveLoans() {
  const [searchTerm, setSearchTerm] = useState('')

  const filteredLoans = mockLoans.filter(loan =>
    loan.borrowerName.toLowerCase().includes(searchTerm.toLowerCase()) ||
    loan.nic.includes(searchTerm)
  )

  const totalOutstanding = mockLoans.reduce((sum, loan) => sum + loan.outstandingBalance, 0)
  const totalLoans = mockLoans.length
  const overdueCount = mockLoans.filter(l => l.status === 'overdue').length

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-secondary-900">Active Loans</h1>
        <p className="text-secondary-600 mt-1">Monitor and manage active loan accounts</p>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div className="card">
          <div className="card-body flex items-center gap-4">
            <div className="p-3 bg-primary-100 rounded-xl">
              <DollarSign className="h-6 w-6 text-primary-600" />
            </div>
            <div>
              <p className="text-sm text-secondary-600">Total Outstanding</p>
              <p className="text-2xl font-bold text-secondary-900">Rs. {totalOutstanding.toLocaleString()}</p>
            </div>
          </div>
        </div>
        <div className="card">
          <div className="card-body flex items-center gap-4">
            <div className="p-3 bg-green-100 rounded-xl">
              <TrendingUp className="h-6 w-6 text-green-600" />
            </div>
            <div>
              <p className="text-sm text-secondary-600">Active Loans</p>
              <p className="text-2xl font-bold text-secondary-900">{totalLoans}</p>
            </div>
          </div>
        </div>
        <div className="card">
          <div className="card-body flex items-center gap-4">
            <div className="p-3 bg-red-100 rounded-xl">
              <Calendar className="h-6 w-6 text-red-600" />
            </div>
            <div>
              <p className="text-sm text-secondary-600">Overdue</p>
              <p className="text-2xl font-bold text-secondary-900">{overdueCount}</p>
            </div>
          </div>
        </div>
      </div>

      <div className="card">
        <div className="card-body">
          <div className="flex flex-col sm:flex-row gap-4 mb-6">
            <div className="relative flex-1">
              <Search className="absolute left-3 top-1/2 -translate-y-1/2 h-5 w-5 text-secondary-400" />
              <input
                type="text"
                placeholder="Search by name or NIC..."
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                className="input pl-10 w-full"
              />
            </div>
          </div>

          <div className="overflow-x-auto">
            <table className="w-full">
              <thead>
                <tr className="border-b border-secondary-200">
                  <th className="text-left py-3 px-4 font-semibold text-secondary-700">Borrower</th>
                  <th className="text-left py-3 px-4 font-semibold text-secondary-700">Loan Amount</th>
                  <th className="text-left py-3 px-4 font-semibold text-secondary-700">Outstanding</th>
                  <th className="text-left py-3 px-4 font-semibold text-secondary-700">Monthly</th>
                  <th className="text-left py-3 px-4 font-semibold text-secondary-700">Rate</th>
                  <th className="text-left py-3 px-4 font-semibold text-secondary-700">Next Payment</th>
                  <th className="text-left py-3 px-4 font-semibold text-secondary-700">Status</th>
                  <th className="text-left py-3 px-4 font-semibold text-secondary-700">Actions</th>
                </tr>
              </thead>
              <tbody>
                {filteredLoans.map((loan) => {
                  const status = statusConfig[loan.status]
                  return (
                    <tr key={loan.id} className="border-b border-secondary-100 hover:bg-secondary-50">
                      <td className="py-3 px-4">
                        <div className="font-medium text-secondary-900">{loan.borrowerName}</div>
                        <div className="text-sm text-secondary-500">{loan.nic}</div>
                      </td>
                      <td className="py-3 px-4 text-secondary-900">Rs. {loan.loanAmount.toLocaleString()}</td>
                      <td className="py-3 px-4 font-medium text-secondary-900">Rs. {loan.outstandingBalance.toLocaleString()}</td>
                      <td className="py-3 px-4 text-secondary-600">Rs. {loan.monthlyPayment.toLocaleString()}</td>
                      <td className="py-3 px-4 text-secondary-600">{loan.interestRate}%</td>
                      <td className="py-3 px-4 text-secondary-600">{loan.nextPaymentDate}</td>
                      <td className="py-3 px-4">
                        <span className={`px-2 py-1 rounded-full text-xs font-medium ${status.color}`}>
                          {status.label}
                        </span>
                      </td>
                      <td className="py-3 px-4">
                        <button className="p-2 hover:bg-secondary-100 rounded-lg">
                          <Eye className="h-4 w-4 text-secondary-500" />
                        </button>
                      </td>
                    </tr>
                  )
                })}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  )
}
