import { Download, TrendingUp, Users, Wallet, AlertTriangle, PieChart, BarChart3 } from 'lucide-react'

export default function LoanStatistics() {
  const stats = {
    totalLoans: 1250,
    activeLoans: 980,
    completedLoans: 220,
    defaultedLoans: 50,
    avgLoanAmount: 350000,
    avgInterestRate: 12.5,
    totalPortfolio: 450000000,
    nplRatio: 4.2,
  }

  const loansByPurpose = [
    { purpose: 'Business', count: 450, percentage: 36 },
    { purpose: 'Education', count: 280, percentage: 22 },
    { purpose: 'Housing', count: 250, percentage: 20 },
    { purpose: 'Personal', count: 180, percentage: 14 },
    { purpose: 'Other', count: 90, percentage: 8 },
  ]

  const loansByBranch = [
    { branch: 'Colombo', count: 420, amount: 150000000 },
    { branch: 'Kandy', count: 280, amount: 95000000 },
    { branch: 'Galle', count: 220, amount: 78000000 },
    { branch: 'Jaffna', count: 180, amount: 62000000 },
    { branch: 'Kurunegala', count: 150, amount: 65000000 },
  ]

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold text-secondary-900">Loan Statistics</h1>
          <p className="text-secondary-600 mt-1">Comprehensive loan portfolio analysis</p>
        </div>
        <button className="btn btn-primary flex items-center gap-2">
          <Download className="h-4 w-4" />
          Export Report
        </button>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        <div className="card">
          <div className="card-body">
            <div className="flex items-center gap-3">
              <div className="p-3 bg-blue-100 rounded-xl">
                <Wallet className="h-6 w-6 text-blue-600" />
              </div>
              <div>
                <p className="text-sm text-secondary-600">Total Portfolio</p>
                <p className="text-xl font-bold text-secondary-900">Rs. {(stats.totalPortfolio / 1000000).toFixed(0)}M</p>
              </div>
            </div>
          </div>
        </div>

        <div className="card">
          <div className="card-body">
            <div className="flex items-center gap-3">
              <div className="p-3 bg-green-100 rounded-xl">
                <Users className="h-6 w-6 text-green-600" />
              </div>
              <div>
                <p className="text-sm text-secondary-600">Active Loans</p>
                <p className="text-xl font-bold text-secondary-900">{stats.activeLoans}</p>
              </div>
            </div>
          </div>
        </div>

        <div className="card">
          <div className="card-body">
            <div className="flex items-center gap-3">
              <div className="p-3 bg-purple-100 rounded-xl">
                <TrendingUp className="h-6 w-6 text-purple-600" />
              </div>
              <div>
                <p className="text-sm text-secondary-600">Avg. Interest Rate</p>
                <p className="text-xl font-bold text-secondary-900">{stats.avgInterestRate}%</p>
              </div>
            </div>
          </div>
        </div>

        <div className="card">
          <div className="card-body">
            <div className="flex items-center gap-3">
              <div className="p-3 bg-red-100 rounded-xl">
                <AlertTriangle className="h-6 w-6 text-red-600" />
              </div>
              <div>
                <p className="text-sm text-secondary-600">NPL Ratio</p>
                <p className="text-xl font-bold text-secondary-900">{stats.nplRatio}%</p>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <div className="card">
          <div className="card-body">
            <div className="flex items-center justify-between mb-4">
              <h3 className="text-lg font-semibold text-secondary-900">Loans by Purpose</h3>
              <PieChart className="h-5 w-5 text-secondary-400" />
            </div>
            <div className="space-y-4">
              {loansByPurpose.map((item, i) => (
                <div key={i} className="flex items-center gap-4">
                  <div className="w-24 text-sm text-secondary-600">{item.purpose}</div>
                  <div className="flex-1">
                    <div className="bg-secondary-200 rounded-full h-3">
                      <div 
                        className="bg-primary-500 h-3 rounded-full" 
                        style={{ width: `${item.percentage}%` }} 
                      />
                    </div>
                  </div>
                  <div className="w-16 text-right">
                    <span className="text-sm font-medium text-secondary-900">{item.count}</span>
                    <span className="text-xs text-secondary-500 ml-1">({item.percentage}%)</span>
                  </div>
                </div>
              ))}
            </div>
          </div>
        </div>

        <div className="card">
          <div className="card-body">
            <div className="flex items-center justify-between mb-4">
              <h3 className="text-lg font-semibold text-secondary-900">Loans by Branch</h3>
              <BarChart3 className="h-5 w-5 text-secondary-400" />
            </div>
            <div className="overflow-x-auto">
              <table className="w-full">
                <thead>
                  <tr className="border-b border-secondary-200">
                    <th className="text-left py-2 text-sm font-semibold text-secondary-700">Branch</th>
                    <th className="text-right py-2 text-sm font-semibold text-secondary-700">Loans</th>
                    <th className="text-right py-2 text-sm font-semibold text-secondary-700">Amount</th>
                  </tr>
                </thead>
                <tbody>
                  {loansByBranch.map((item, i) => (
                    <tr key={i} className="border-b border-secondary-100">
                      <td className="py-3 font-medium text-secondary-900">{item.branch}</td>
                      <td className="py-3 text-right text-secondary-600">{item.count}</td>
                      <td className="py-3 text-right text-secondary-900 font-medium">Rs. {(item.amount / 1000000).toFixed(0)}M</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>

      <div className="card">
        <div className="card-body">
          <h3 className="text-lg font-semibold text-secondary-900 mb-4">Loan Distribution Overview</h3>
          <div className="grid grid-cols-2 md:grid-cols-4 gap-6">
            <div className="text-center p-4 bg-secondary-50 rounded-lg">
              <p className="text-3xl font-bold text-secondary-900">{stats.totalLoans}</p>
              <p className="text-sm text-secondary-600 mt-1">Total Loans</p>
            </div>
            <div className="text-center p-4 bg-green-50 rounded-lg">
              <p className="text-3xl font-bold text-green-600">{stats.activeLoans}</p>
              <p className="text-sm text-secondary-600 mt-1">Active</p>
            </div>
            <div className="text-center p-4 bg-blue-50 rounded-lg">
              <p className="text-3xl font-bold text-blue-600">{stats.completedLoans}</p>
              <p className="text-sm text-secondary-600 mt-1">Completed</p>
            </div>
            <div className="text-center p-4 bg-red-50 rounded-lg">
              <p className="text-3xl font-bold text-red-600">{stats.defaultedLoans}</p>
              <p className="text-sm text-secondary-600 mt-1">Defaulted</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}
