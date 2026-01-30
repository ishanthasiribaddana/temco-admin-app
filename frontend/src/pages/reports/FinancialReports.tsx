import { useState } from 'react'
import { Download, Calendar, TrendingUp, DollarSign, PieChart } from 'lucide-react'

export default function FinancialReports() {
  const [dateRange, setDateRange] = useState('this_month')

  const summaryData = {
    totalDisbursed: 15000000,
    totalCollected: 8500000,
    interestEarned: 1200000,
    outstandingBalance: 45000000,
    disbursedChange: 12.5,
    collectedChange: 8.3,
  }

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold text-secondary-900">Financial Reports</h1>
          <p className="text-secondary-600 mt-1">Overview of financial performance</p>
        </div>
        <div className="flex items-center gap-4">
          <select
            value={dateRange}
            onChange={(e) => setDateRange(e.target.value)}
            className="input"
          >
            <option value="today">Today</option>
            <option value="this_week">This Week</option>
            <option value="this_month">This Month</option>
            <option value="this_quarter">This Quarter</option>
            <option value="this_year">This Year</option>
          </select>
          <button className="btn btn-primary flex items-center gap-2">
            <Download className="h-4 w-4" />
            Export
          </button>
        </div>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        <div className="card">
          <div className="card-body">
            <div className="flex items-center justify-between">
              <div className="p-3 bg-blue-100 rounded-xl">
                <DollarSign className="h-6 w-6 text-blue-600" />
              </div>
              <div className="flex items-center text-green-600 text-sm">
                <TrendingUp className="h-4 w-4 mr-1" />
                {summaryData.disbursedChange}%
              </div>
            </div>
            <div className="mt-4">
              <p className="text-sm text-secondary-600">Total Disbursed</p>
              <p className="text-2xl font-bold text-secondary-900">Rs. {(summaryData.totalDisbursed / 1000000).toFixed(1)}M</p>
            </div>
          </div>
        </div>

        <div className="card">
          <div className="card-body">
            <div className="flex items-center justify-between">
              <div className="p-3 bg-green-100 rounded-xl">
                <TrendingUp className="h-6 w-6 text-green-600" />
              </div>
              <div className="flex items-center text-green-600 text-sm">
                <TrendingUp className="h-4 w-4 mr-1" />
                {summaryData.collectedChange}%
              </div>
            </div>
            <div className="mt-4">
              <p className="text-sm text-secondary-600">Total Collected</p>
              <p className="text-2xl font-bold text-secondary-900">Rs. {(summaryData.totalCollected / 1000000).toFixed(1)}M</p>
            </div>
          </div>
        </div>

        <div className="card">
          <div className="card-body">
            <div className="flex items-center justify-between">
              <div className="p-3 bg-purple-100 rounded-xl">
                <PieChart className="h-6 w-6 text-purple-600" />
              </div>
            </div>
            <div className="mt-4">
              <p className="text-sm text-secondary-600">Interest Earned</p>
              <p className="text-2xl font-bold text-secondary-900">Rs. {(summaryData.interestEarned / 1000000).toFixed(1)}M</p>
            </div>
          </div>
        </div>

        <div className="card">
          <div className="card-body">
            <div className="flex items-center justify-between">
              <div className="p-3 bg-orange-100 rounded-xl">
                <Calendar className="h-6 w-6 text-orange-600" />
              </div>
            </div>
            <div className="mt-4">
              <p className="text-sm text-secondary-600">Outstanding Balance</p>
              <p className="text-2xl font-bold text-secondary-900">Rs. {(summaryData.outstandingBalance / 1000000).toFixed(1)}M</p>
            </div>
          </div>
        </div>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <div className="card">
          <div className="card-body">
            <h3 className="text-lg font-semibold text-secondary-900 mb-4">Monthly Disbursements</h3>
            <div className="h-64 flex items-center justify-center bg-secondary-50 rounded-lg">
              <p className="text-secondary-500">Chart will be rendered here</p>
            </div>
          </div>
        </div>

        <div className="card">
          <div className="card-body">
            <h3 className="text-lg font-semibold text-secondary-900 mb-4">Collections vs Targets</h3>
            <div className="h-64 flex items-center justify-center bg-secondary-50 rounded-lg">
              <p className="text-secondary-500">Chart will be rendered here</p>
            </div>
          </div>
        </div>
      </div>

      <div className="card">
        <div className="card-body">
          <h3 className="text-lg font-semibold text-secondary-900 mb-4">Branch Performance</h3>
          <div className="overflow-x-auto">
            <table className="w-full">
              <thead>
                <tr className="border-b border-secondary-200">
                  <th className="text-left py-3 px-4 font-semibold text-secondary-700">Branch</th>
                  <th className="text-left py-3 px-4 font-semibold text-secondary-700">Disbursed</th>
                  <th className="text-left py-3 px-4 font-semibold text-secondary-700">Collected</th>
                  <th className="text-left py-3 px-4 font-semibold text-secondary-700">Outstanding</th>
                  <th className="text-left py-3 px-4 font-semibold text-secondary-700">Collection Rate</th>
                </tr>
              </thead>
              <tbody>
                {[
                  { branch: 'Colombo', disbursed: 5000000, collected: 3000000, outstanding: 15000000, rate: 92 },
                  { branch: 'Kandy', disbursed: 3500000, collected: 2000000, outstanding: 10000000, rate: 88 },
                  { branch: 'Galle', disbursed: 2500000, collected: 1500000, outstanding: 8000000, rate: 85 },
                  { branch: 'Jaffna', disbursed: 2000000, collected: 1000000, outstanding: 6000000, rate: 78 },
                ].map((row, i) => (
                  <tr key={i} className="border-b border-secondary-100 hover:bg-secondary-50">
                    <td className="py-3 px-4 font-medium text-secondary-900">{row.branch}</td>
                    <td className="py-3 px-4 text-secondary-600">Rs. {(row.disbursed / 1000000).toFixed(1)}M</td>
                    <td className="py-3 px-4 text-secondary-600">Rs. {(row.collected / 1000000).toFixed(1)}M</td>
                    <td className="py-3 px-4 text-secondary-600">Rs. {(row.outstanding / 1000000).toFixed(1)}M</td>
                    <td className="py-3 px-4">
                      <div className="flex items-center gap-2">
                        <div className="flex-1 bg-secondary-200 rounded-full h-2">
                          <div className="bg-green-500 h-2 rounded-full" style={{ width: `${row.rate}%` }} />
                        </div>
                        <span className="text-sm font-medium text-secondary-700">{row.rate}%</span>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  )
}
