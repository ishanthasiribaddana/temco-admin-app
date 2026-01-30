import { useState } from 'react'
import { Search, Filter, Eye, CheckCircle, XCircle, Clock } from 'lucide-react'

interface LoanApplication {
  id: number
  applicantName: string
  nic: string
  amount: number
  purpose: string
  status: 'pending' | 'approved' | 'rejected' | 'under_review'
  appliedDate: string
  branch: string
}

const mockApplications: LoanApplication[] = [
  { id: 1, applicantName: 'John Perera', nic: '199012345678', amount: 500000, purpose: 'Business Expansion', status: 'pending', appliedDate: '2026-01-28', branch: 'Colombo' },
  { id: 2, applicantName: 'Mary Silva', nic: '198523456789', amount: 250000, purpose: 'Education', status: 'approved', appliedDate: '2026-01-25', branch: 'Kandy' },
  { id: 3, applicantName: 'Kumar Fernando', nic: '199234567890', amount: 1000000, purpose: 'Housing', status: 'under_review', appliedDate: '2026-01-27', branch: 'Galle' },
  { id: 4, applicantName: 'Saman Jayawardena', nic: '198745678901', amount: 150000, purpose: 'Personal', status: 'rejected', appliedDate: '2026-01-20', branch: 'Colombo' },
]

const statusConfig = {
  pending: { label: 'Pending', color: 'bg-yellow-100 text-yellow-800', icon: Clock },
  approved: { label: 'Approved', color: 'bg-green-100 text-green-800', icon: CheckCircle },
  rejected: { label: 'Rejected', color: 'bg-red-100 text-red-800', icon: XCircle },
  under_review: { label: 'Under Review', color: 'bg-blue-100 text-blue-800', icon: Eye },
}

export default function LoanApplications() {
  const [searchTerm, setSearchTerm] = useState('')
  const [statusFilter, setStatusFilter] = useState<string>('all')

  const filteredApplications = mockApplications.filter(app => {
    const matchesSearch = app.applicantName.toLowerCase().includes(searchTerm.toLowerCase()) ||
                          app.nic.includes(searchTerm)
    const matchesStatus = statusFilter === 'all' || app.status === statusFilter
    return matchesSearch && matchesStatus
  })

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold text-secondary-900">Loan Applications</h1>
          <p className="text-secondary-600 mt-1">Review and manage loan applications</p>
        </div>
        <div className="flex items-center gap-4">
          <span className="px-3 py-1 bg-yellow-100 text-yellow-800 rounded-full text-sm font-medium">
            {mockApplications.filter(a => a.status === 'pending').length} Pending
          </span>
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
            <div className="flex items-center gap-2">
              <Filter className="h-5 w-5 text-secondary-400" />
              <select
                value={statusFilter}
                onChange={(e) => setStatusFilter(e.target.value)}
                className="input"
              >
                <option value="all">All Status</option>
                <option value="pending">Pending</option>
                <option value="approved">Approved</option>
                <option value="rejected">Rejected</option>
                <option value="under_review">Under Review</option>
              </select>
            </div>
          </div>

          <div className="overflow-x-auto">
            <table className="w-full">
              <thead>
                <tr className="border-b border-secondary-200">
                  <th className="text-left py-3 px-4 font-semibold text-secondary-700">Applicant</th>
                  <th className="text-left py-3 px-4 font-semibold text-secondary-700">NIC</th>
                  <th className="text-left py-3 px-4 font-semibold text-secondary-700">Amount</th>
                  <th className="text-left py-3 px-4 font-semibold text-secondary-700">Purpose</th>
                  <th className="text-left py-3 px-4 font-semibold text-secondary-700">Branch</th>
                  <th className="text-left py-3 px-4 font-semibold text-secondary-700">Status</th>
                  <th className="text-left py-3 px-4 font-semibold text-secondary-700">Actions</th>
                </tr>
              </thead>
              <tbody>
                {filteredApplications.map((app) => {
                  const status = statusConfig[app.status]
                  const StatusIcon = status.icon
                  return (
                    <tr key={app.id} className="border-b border-secondary-100 hover:bg-secondary-50">
                      <td className="py-3 px-4">
                        <div className="font-medium text-secondary-900">{app.applicantName}</div>
                        <div className="text-sm text-secondary-500">{app.appliedDate}</div>
                      </td>
                      <td className="py-3 px-4 text-secondary-600">{app.nic}</td>
                      <td className="py-3 px-4 font-medium text-secondary-900">
                        Rs. {app.amount.toLocaleString()}
                      </td>
                      <td className="py-3 px-4 text-secondary-600">{app.purpose}</td>
                      <td className="py-3 px-4 text-secondary-600">{app.branch}</td>
                      <td className="py-3 px-4">
                        <span className={`inline-flex items-center gap-1 px-2 py-1 rounded-full text-xs font-medium ${status.color}`}>
                          <StatusIcon className="h-3 w-3" />
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
