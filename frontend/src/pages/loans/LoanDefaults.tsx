import { useState } from 'react'
import { Search, AlertTriangle, Phone, Mail, FileText } from 'lucide-react'

interface DefaultedLoan {
  id: number
  borrowerName: string
  nic: string
  phone: string
  email: string
  loanAmount: number
  outstandingBalance: number
  daysOverdue: number
  lastPaymentDate: string
  defaultDate: string
  recoveryStatus: 'initial_contact' | 'negotiation' | 'legal_action' | 'written_off'
}

const mockDefaults: DefaultedLoan[] = [
  { id: 1, borrowerName: 'Kamal Rathnayake', nic: '198812345678', phone: '0771234567', email: 'kamal@email.com', loanAmount: 300000, outstandingBalance: 280000, daysOverdue: 45, lastPaymentDate: '2025-12-15', defaultDate: '2026-01-15', recoveryStatus: 'initial_contact' },
  { id: 2, borrowerName: 'Sunitha Bandara', nic: '199023456789', phone: '0772345678', email: 'sunitha@email.com', loanAmount: 500000, outstandingBalance: 450000, daysOverdue: 90, lastPaymentDate: '2025-10-30', defaultDate: '2025-11-30', recoveryStatus: 'negotiation' },
  { id: 3, borrowerName: 'Pradeep Kumara', nic: '198534567890', phone: '0773456789', email: 'pradeep@email.com', loanAmount: 750000, outstandingBalance: 720000, daysOverdue: 180, lastPaymentDate: '2025-07-20', defaultDate: '2025-08-20', recoveryStatus: 'legal_action' },
]

const statusConfig = {
  initial_contact: { label: 'Initial Contact', color: 'bg-yellow-100 text-yellow-800' },
  negotiation: { label: 'Negotiation', color: 'bg-blue-100 text-blue-800' },
  legal_action: { label: 'Legal Action', color: 'bg-red-100 text-red-800' },
  written_off: { label: 'Written Off', color: 'bg-gray-100 text-gray-800' },
}

export default function LoanDefaults() {
  const [searchTerm, setSearchTerm] = useState('')

  const filteredDefaults = mockDefaults.filter(loan =>
    loan.borrowerName.toLowerCase().includes(searchTerm.toLowerCase()) ||
    loan.nic.includes(searchTerm)
  )

  const totalDefaulted = mockDefaults.reduce((sum, loan) => sum + loan.outstandingBalance, 0)

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold text-secondary-900">Loan Defaults</h1>
          <p className="text-secondary-600 mt-1">Manage defaulted loans and recovery process</p>
        </div>
        <div className="flex items-center gap-2 px-4 py-2 bg-red-50 border border-red-200 rounded-lg">
          <AlertTriangle className="h-5 w-5 text-red-500" />
          <span className="text-red-700 font-medium">Total at Risk: Rs. {totalDefaulted.toLocaleString()}</span>
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

          <div className="space-y-4">
            {filteredDefaults.map((loan) => {
              const status = statusConfig[loan.recoveryStatus]
              return (
                <div key={loan.id} className="border border-secondary-200 rounded-lg p-4 hover:bg-secondary-50">
                  <div className="flex flex-col lg:flex-row lg:items-center justify-between gap-4">
                    <div className="flex-1">
                      <div className="flex items-center gap-3 mb-2">
                        <h3 className="font-semibold text-secondary-900">{loan.borrowerName}</h3>
                        <span className={`px-2 py-1 rounded-full text-xs font-medium ${status.color}`}>
                          {status.label}
                        </span>
                      </div>
                      <div className="grid grid-cols-2 md:grid-cols-4 gap-4 text-sm">
                        <div>
                          <p className="text-secondary-500">NIC</p>
                          <p className="font-medium text-secondary-900">{loan.nic}</p>
                        </div>
                        <div>
                          <p className="text-secondary-500">Outstanding</p>
                          <p className="font-medium text-red-600">Rs. {loan.outstandingBalance.toLocaleString()}</p>
                        </div>
                        <div>
                          <p className="text-secondary-500">Days Overdue</p>
                          <p className="font-medium text-secondary-900">{loan.daysOverdue} days</p>
                        </div>
                        <div>
                          <p className="text-secondary-500">Default Date</p>
                          <p className="font-medium text-secondary-900">{loan.defaultDate}</p>
                        </div>
                      </div>
                    </div>
                    <div className="flex items-center gap-2">
                      <button className="p-2 hover:bg-secondary-100 rounded-lg" title="Call">
                        <Phone className="h-4 w-4 text-secondary-500" />
                      </button>
                      <button className="p-2 hover:bg-secondary-100 rounded-lg" title="Email">
                        <Mail className="h-4 w-4 text-secondary-500" />
                      </button>
                      <button className="p-2 hover:bg-secondary-100 rounded-lg" title="View Details">
                        <FileText className="h-4 w-4 text-secondary-500" />
                      </button>
                    </div>
                  </div>
                </div>
              )
            })}
          </div>
        </div>
      </div>
    </div>
  )
}
