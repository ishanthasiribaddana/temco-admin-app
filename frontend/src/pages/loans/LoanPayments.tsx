import { useState } from 'react'
import { Search, CheckCircle, Clock, AlertCircle, Download } from 'lucide-react'

interface Payment {
  id: number
  borrowerName: string
  loanId: number
  amount: number
  paymentDate: string
  dueDate: string
  method: string
  status: 'completed' | 'pending' | 'failed'
  reference: string
}

const mockPayments: Payment[] = [
  { id: 1, borrowerName: 'John Perera', loanId: 1001, amount: 15000, paymentDate: '2026-01-28', dueDate: '2026-01-28', method: 'Bank Transfer', status: 'completed', reference: 'PAY-2026-0128-001' },
  { id: 2, borrowerName: 'Mary Silva', loanId: 1002, amount: 8500, paymentDate: '2026-01-27', dueDate: '2026-01-25', method: 'Cash', status: 'completed', reference: 'PAY-2026-0127-002' },
  { id: 3, borrowerName: 'Kumar Fernando', loanId: 1003, amount: 35000, paymentDate: '', dueDate: '2026-01-25', method: '', status: 'pending', reference: 'PAY-2026-0125-003' },
  { id: 4, borrowerName: 'Nimal Jayasinghe', loanId: 1004, amount: 22000, paymentDate: '2026-01-20', dueDate: '2026-01-20', method: 'Online', status: 'completed', reference: 'PAY-2026-0120-004' },
]

const statusConfig = {
  completed: { label: 'Completed', color: 'bg-green-100 text-green-800', icon: CheckCircle },
  pending: { label: 'Pending', color: 'bg-yellow-100 text-yellow-800', icon: Clock },
  failed: { label: 'Failed', color: 'bg-red-100 text-red-800', icon: AlertCircle },
}

export default function LoanPayments() {
  const [searchTerm, setSearchTerm] = useState('')

  const filteredPayments = mockPayments.filter(payment =>
    payment.borrowerName.toLowerCase().includes(searchTerm.toLowerCase()) ||
    payment.reference.toLowerCase().includes(searchTerm.toLowerCase())
  )

  const totalCollected = mockPayments.filter(p => p.status === 'completed').reduce((sum, p) => sum + p.amount, 0)
  const pendingAmount = mockPayments.filter(p => p.status === 'pending').reduce((sum, p) => sum + p.amount, 0)

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold text-secondary-900">Loan Payments</h1>
          <p className="text-secondary-600 mt-1">Track and manage loan payments</p>
        </div>
        <button className="btn btn-primary flex items-center gap-2">
          <Download className="h-4 w-4" />
          Export Report
        </button>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        <div className="card">
          <div className="card-body">
            <p className="text-sm text-secondary-600">This Month Collected</p>
            <p className="text-3xl font-bold text-green-600">Rs. {totalCollected.toLocaleString()}</p>
          </div>
        </div>
        <div className="card">
          <div className="card-body">
            <p className="text-sm text-secondary-600">Pending Payments</p>
            <p className="text-3xl font-bold text-yellow-600">Rs. {pendingAmount.toLocaleString()}</p>
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
                placeholder="Search by name or reference..."
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
                  <th className="text-left py-3 px-4 font-semibold text-secondary-700">Reference</th>
                  <th className="text-left py-3 px-4 font-semibold text-secondary-700">Borrower</th>
                  <th className="text-left py-3 px-4 font-semibold text-secondary-700">Amount</th>
                  <th className="text-left py-3 px-4 font-semibold text-secondary-700">Due Date</th>
                  <th className="text-left py-3 px-4 font-semibold text-secondary-700">Payment Date</th>
                  <th className="text-left py-3 px-4 font-semibold text-secondary-700">Method</th>
                  <th className="text-left py-3 px-4 font-semibold text-secondary-700">Status</th>
                </tr>
              </thead>
              <tbody>
                {filteredPayments.map((payment) => {
                  const status = statusConfig[payment.status]
                  const StatusIcon = status.icon
                  return (
                    <tr key={payment.id} className="border-b border-secondary-100 hover:bg-secondary-50">
                      <td className="py-3 px-4 font-mono text-sm text-secondary-600">{payment.reference}</td>
                      <td className="py-3 px-4">
                        <div className="font-medium text-secondary-900">{payment.borrowerName}</div>
                        <div className="text-sm text-secondary-500">Loan #{payment.loanId}</div>
                      </td>
                      <td className="py-3 px-4 font-medium text-secondary-900">Rs. {payment.amount.toLocaleString()}</td>
                      <td className="py-3 px-4 text-secondary-600">{payment.dueDate}</td>
                      <td className="py-3 px-4 text-secondary-600">{payment.paymentDate || '-'}</td>
                      <td className="py-3 px-4 text-secondary-600">{payment.method || '-'}</td>
                      <td className="py-3 px-4">
                        <span className={`inline-flex items-center gap-1 px-2 py-1 rounded-full text-xs font-medium ${status.color}`}>
                          <StatusIcon className="h-3 w-3" />
                          {status.label}
                        </span>
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
