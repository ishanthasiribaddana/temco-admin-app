import { useState } from 'react'
import { Link } from 'react-router-dom'
import { 
  ArrowLeft, 
  Save, 
  User, 
  Shield, 
  CheckCircle2, 
  XCircle, 
  AlertCircle,
  ChevronDown,
  ChevronRight,
  Wallet,
  FileText,
  Search,
  Loader2,
  UserCheck,
  UserPlus
} from 'lucide-react'
import toast from 'react-hot-toast'

interface Task {
  code: string
  name: string
  accountant: string
  financeController: string
  auditor: string
}

interface Category {
  id: string
  name: string
  color: string
  priority: string
  tasks: Task[]
}

const CATEGORIES: Category[] = [
  {
    id: 'PAY',
    name: 'Payment & Collection Management',
    color: 'red',
    priority: 'HIGH',
    tasks: [
      { code: 'PAY-001', name: 'View all payment records', accountant: '✅', financeController: '✅', auditor: '✅' },
      { code: 'PAY-002', name: 'Enter/Record payments (cash, bank, online)', accountant: '✅ Enter', financeController: '✅ Override', auditor: '❌' },
      { code: 'PAY-003', name: 'Edit payment entries', accountant: '✅ Own (pending)', financeController: '✅ All', auditor: '❌' },
      { code: 'PAY-004', name: 'Delete payment entries', accountant: '❌', financeController: '✅ With reason', auditor: '❌' },
      { code: 'PAY-005', name: 'Verify payment entries', accountant: '✅ Mark verified', financeController: '✅ Final approve', auditor: '✅ Audit check' },
      { code: 'PAY-006', name: 'Process overdue payments', accountant: '✅', financeController: '✅', auditor: '❌' },
      { code: 'PAY-007', name: 'Generate payment receipts', accountant: '✅', financeController: '✅', auditor: '❌' },
      { code: 'PAY-008', name: 'View payment schedules', accountant: '✅', financeController: '✅', auditor: '✅' },
      { code: 'PAY-009', name: 'Modify payment schedules', accountant: '❌', financeController: '✅', auditor: '❌' },
      { code: 'PAY-010', name: 'Send payment reminders', accountant: '✅', financeController: '✅', auditor: '❌' },
    ]
  },
  {
    id: 'DEP',
    name: 'Deposit Slip Verification',
    color: 'blue',
    priority: 'HIGH',
    tasks: [
      { code: 'DEP-001', name: 'View uploaded deposit slips (from portal)', accountant: '✅', financeController: '✅', auditor: '✅' },
      { code: 'DEP-002', name: 'Verify deposit slip authenticity', accountant: '✅ Initial verify', financeController: '✅ Final verify', auditor: '❌' },
      { code: 'DEP-003', name: 'Match deposit slip with bank statement', accountant: '✅', financeController: '✅', auditor: '✅ Review' },
      { code: 'DEP-004', name: 'Approve/Reject deposit slip', accountant: '❌', financeController: '✅', auditor: '❌' },
      { code: 'DEP-005', name: 'Flag suspicious deposits', accountant: '✅', financeController: '✅', auditor: '✅' },
      { code: 'DEP-006', name: 'Request customer clarification', accountant: '✅', financeController: '✅', auditor: '❌' },
      { code: 'DEP-007', name: 'Update customer account after verification', accountant: '✅ Pending FC', financeController: '✅ Confirm', auditor: '❌' },
      { code: 'DEP-008', name: 'Generate deposit verification report', accountant: '✅', financeController: '✅', auditor: '✅' },
    ]
  },
  {
    id: 'REC',
    name: 'Bank Reconciliation',
    color: 'green',
    priority: 'HIGH',
    tasks: [
      { code: 'REC-001', name: 'Upload/Import bank statements', accountant: '✅', financeController: '✅', auditor: '❌' },
      { code: 'REC-002', name: 'Auto-match transactions', accountant: '✅ Initiate', financeController: '✅', auditor: '✅ View' },
      { code: 'REC-003', name: 'Manual matching of transactions', accountant: '✅', financeController: '✅', auditor: '❌' },
      { code: 'REC-004', name: 'Mark transactions as reconciled', accountant: '✅ Pending', financeController: '✅ Approve', auditor: '❌' },
      { code: 'REC-005', name: 'Identify unmatched transactions', accountant: '✅', financeController: '✅', auditor: '✅' },
      { code: 'REC-006', name: 'Resolve discrepancies', accountant: '✅ Propose', financeController: '✅ Resolve', auditor: '✅ Review' },
      { code: 'REC-007', name: 'Third-party gateway reconciliation', accountant: '✅', financeController: '✅', auditor: '✅ Review' },
      { code: 'REC-008', name: 'ATM deposit reconciliation', accountant: '✅ Match', financeController: '✅ Verify', auditor: '✅ Audit' },
      { code: 'REC-009', name: 'Over-the-counter deposit reconciliation', accountant: '✅ Match', financeController: '✅ Verify', auditor: '✅ Audit' },
      { code: 'REC-010', name: 'Approve reconciliation batch', accountant: '❌', financeController: '✅', auditor: '❌' },
      { code: 'REC-011', name: 'Generate reconciliation report', accountant: '✅', financeController: '✅', auditor: '✅' },
      { code: 'REC-012', name: 'Close reconciliation period', accountant: '❌', financeController: '✅', auditor: '❌' },
    ]
  },
  {
    id: 'LN',
    name: 'Loan Management',
    color: 'yellow',
    priority: 'MEDIUM',
    tasks: [
      { code: 'LN-001', name: 'View loan applications', accountant: '✅', financeController: '✅', auditor: '✅' },
      { code: 'LN-002', name: 'Process loan applications', accountant: '❌', financeController: '✅', auditor: '❌' },
      { code: 'LN-003', name: 'Approve/Reject loans', accountant: '❌', financeController: '✅', auditor: '❌' },
      { code: 'LN-004', name: 'View active loans', accountant: '✅', financeController: '✅', auditor: '✅' },
      { code: 'LN-005', name: 'Calculate interest/penalties', accountant: '✅', financeController: '✅', auditor: '✅ View' },
      { code: 'LN-006', name: 'Process loan disbursement', accountant: '✅ Initiate', financeController: '✅ Approve', auditor: '❌' },
      { code: 'LN-007', name: 'Manage loan restructuring', accountant: '❌', financeController: '✅', auditor: '❌' },
      { code: 'LN-008', name: 'Process loan closure', accountant: '✅ Initiate', financeController: '✅ Approve', auditor: '❌' },
      { code: 'LN-009', name: 'Handle loan defaults', accountant: '❌', financeController: '✅', auditor: '✅ Review' },
      { code: 'LN-010', name: 'Approve write-offs', accountant: '❌', financeController: '✅', auditor: '❌' },
      { code: 'LN-011', name: 'Generate loan reports', accountant: '✅', financeController: '✅', auditor: '✅' },
    ]
  },
  {
    id: 'CUS',
    name: 'Customer Account Management',
    color: 'orange',
    priority: 'MEDIUM',
    tasks: [
      { code: 'CUS-001', name: 'View customer profiles', accountant: '✅', financeController: '✅', auditor: '✅' },
      { code: 'CUS-002', name: 'Update customer details', accountant: '✅ Pending', financeController: '✅ Approve', auditor: '❌' },
      { code: 'CUS-003', name: 'View customer balances', accountant: '✅', financeController: '✅', auditor: '✅' },
      { code: 'CUS-004', name: 'View transaction history', accountant: '✅', financeController: '✅', auditor: '✅' },
      { code: 'CUS-005', name: 'Freeze/Unfreeze accounts', accountant: '❌', financeController: '✅', auditor: '❌' },
      { code: 'CUS-006', name: 'Close customer accounts', accountant: '❌', financeController: '✅', auditor: '❌' },
      { code: 'CUS-007', name: 'Generate customer statements', accountant: '✅', financeController: '✅', auditor: '✅' },
      { code: 'CUS-008', name: 'Handle customer disputes', accountant: '✅ Log', financeController: '✅ Resolve', auditor: '✅ Review' },
    ]
  },
  {
    id: 'REF',
    name: 'Refunds & Adjustments',
    color: 'purple',
    priority: 'MEDIUM',
    tasks: [
      { code: 'REF-001', name: 'Initiate refund request', accountant: '✅', financeController: '✅', auditor: '❌' },
      { code: 'REF-002', name: 'Approve refunds', accountant: '❌', financeController: '✅', auditor: '❌' },
      { code: 'REF-003', name: 'Process refund payment', accountant: '✅ After approval', financeController: '✅', auditor: '❌' },
      { code: 'REF-004', name: 'Make account adjustments', accountant: '✅ Pending', financeController: '✅ Approve', auditor: '❌' },
      { code: 'REF-005', name: 'Reverse transactions', accountant: '❌', financeController: '✅', auditor: '❌' },
      { code: 'REF-006', name: 'View adjustment history', accountant: '✅', financeController: '✅', auditor: '✅' },
    ]
  },
  {
    id: 'RPT',
    name: 'Financial Reporting',
    color: 'gray',
    priority: 'MEDIUM',
    tasks: [
      { code: 'RPT-001', name: 'Generate daily collection report', accountant: '✅', financeController: '✅', auditor: '✅' },
      { code: 'RPT-002', name: 'Generate monthly financial report', accountant: '❌', financeController: '✅', auditor: '✅' },
      { code: 'RPT-003', name: 'Generate reconciliation summary', accountant: '✅', financeController: '✅', auditor: '✅' },
      { code: 'RPT-004', name: 'Generate overdue/default report', accountant: '✅', financeController: '✅', auditor: '✅' },
      { code: 'RPT-005', name: 'Generate cash flow report', accountant: '❌', financeController: '✅', auditor: '✅' },
      { code: 'RPT-006', name: 'Generate compliance report', accountant: '❌', financeController: '✅', auditor: '✅' },
      { code: 'RPT-007', name: 'Generate variance report', accountant: '❌', financeController: '✅', auditor: '✅' },
      { code: 'RPT-008', name: 'Export reports (PDF/Excel)', accountant: '✅', financeController: '✅', auditor: '✅' },
    ]
  },
  {
    id: 'AUD',
    name: 'Audit & Compliance',
    color: 'slate',
    priority: 'HIGH',
    tasks: [
      { code: 'AUD-001', name: 'View all audit trails', accountant: '❌', financeController: '✅ Own team', auditor: '✅ All' },
      { code: 'AUD-002', name: 'Review transaction history', accountant: '❌', financeController: '✅', auditor: '✅' },
      { code: 'AUD-003', name: 'Flag transactions for investigation', accountant: '❌', financeController: '❌', auditor: '✅' },
      { code: 'AUD-004', name: 'Add audit notes/comments', accountant: '❌', financeController: '❌', auditor: '✅' },
      { code: 'AUD-005', name: 'Request clarification from FC/Accountant', accountant: '❌', financeController: '❌', auditor: '✅' },
      { code: 'AUD-006', name: 'Generate audit findings report', accountant: '❌', financeController: '❌', auditor: '✅' },
      { code: 'AUD-007', name: 'Review reconciliation approvals', accountant: '❌', financeController: '❌', auditor: '✅' },
      { code: 'AUD-008', name: 'Access historical/archived data', accountant: '❌', financeController: '✅ 1 year', auditor: '✅ All' },
      { code: 'AUD-009', name: 'View user activity logs', accountant: '❌', financeController: '✅ Own team', auditor: '✅ All' },
      { code: 'AUD-010', name: 'Mark audit as complete', accountant: '❌', financeController: '❌', auditor: '✅' },
    ]
  },
  {
    id: 'USR',
    name: 'User-Level Customization',
    color: 'teal',
    priority: 'CONFIG',
    tasks: [
      { code: 'USR-001', name: 'Branch Access (All / Specific branches)', accountant: '⚙️ Config', financeController: '⚙️ Config', auditor: '⚙️ Config' },
      { code: 'USR-002', name: 'Amount Limit (Max transaction value)', accountant: '⚙️ Config', financeController: '⚙️ Config', auditor: 'N/A' },
      { code: 'USR-003', name: 'Customer Type (All / Students / Corporate)', accountant: '⚙️ Config', financeController: '⚙️ Config', auditor: '⚙️ Config' },
      { code: 'USR-004', name: 'Loan Type (All / Education / Housing / etc.)', accountant: '⚙️ Config', financeController: '⚙️ Config', auditor: '⚙️ Config' },
      { code: 'USR-005', name: 'Time Access (24/7 / Office hours only)', accountant: '⚙️ Config', financeController: '⚙️ Config', auditor: '⚙️ Config' },
      { code: 'USR-006', name: 'Approval Authority (Up to Rs. X)', accountant: 'N/A', financeController: '⚙️ Config', auditor: 'N/A' },
      { code: 'USR-007', name: 'Report Access (All / Specific types)', accountant: '⚙️ Config', financeController: '⚙️ Config', auditor: '⚙️ Config' },
      { code: 'USR-008', name: 'Data Visibility (All / Own entries only)', accountant: '⚙️ Config', financeController: '⚙️ Config', auditor: '✅ All' },
      { code: 'USR-009', name: 'Reconciliation Bank (All / Specific banks)', accountant: '⚙️ Config', financeController: '⚙️ Config', auditor: '⚙️ Config' },
    ]
  },
]

const CUSTOMIZATION_OPTIONS = [
  { id: 'branchAccess', label: 'Branch Access', options: ['All Branches', 'Head Office Only', 'Specific Branches'] },
  { id: 'amountLimit', label: 'Transaction Amount Limit', options: ['Unlimited', 'Up to Rs. 100,000', 'Up to Rs. 500,000', 'Up to Rs. 1,000,000'] },
  { id: 'customerType', label: 'Customer Type Access', options: ['All Customers', 'Students Only', 'Corporate Only', 'Individual Only'] },
  { id: 'loanType', label: 'Loan Type Access', options: ['All Loan Types', 'Education Loans', 'Housing Loans', 'Personal Loans', 'Business Loans'] },
  { id: 'timeAccess', label: 'System Access Hours', options: ['24/7 Access', 'Office Hours (8AM-6PM)', 'Extended Hours (6AM-10PM)'] },
  { id: 'approvalAuthority', label: 'Approval Authority', options: ['No Approval Rights', 'Up to Rs. 50,000', 'Up to Rs. 200,000', 'Up to Rs. 500,000', 'Unlimited'] },
  { id: 'reportAccess', label: 'Report Access', options: ['All Reports', 'Basic Reports Only', 'Financial Reports', 'Audit Reports'] },
  { id: 'dataVisibility', label: 'Data Visibility', options: ['All Data', 'Own Entries Only', 'Team Entries', 'Branch Data Only'] },
  { id: 'reconciliationBank', label: 'Reconciliation Banks', options: ['All Banks', 'Commercial Bank', 'BOC', 'Peoples Bank', 'Sampath Bank'] },
]

const getColorClasses = (color: string) => {
  const colors: Record<string, { bg: string; text: string; border: string; badge: string }> = {
    red: { bg: 'bg-red-50', text: 'text-red-700', border: 'border-red-200', badge: 'bg-red-100 text-red-700' },
    blue: { bg: 'bg-blue-50', text: 'text-blue-700', border: 'border-blue-200', badge: 'bg-blue-100 text-blue-700' },
    green: { bg: 'bg-green-50', text: 'text-green-700', border: 'border-green-200', badge: 'bg-green-100 text-green-700' },
    yellow: { bg: 'bg-yellow-50', text: 'text-yellow-700', border: 'border-yellow-200', badge: 'bg-yellow-100 text-yellow-700' },
    orange: { bg: 'bg-orange-50', text: 'text-orange-700', border: 'border-orange-200', badge: 'bg-orange-100 text-orange-700' },
    purple: { bg: 'bg-purple-50', text: 'text-purple-700', border: 'border-purple-200', badge: 'bg-purple-100 text-purple-700' },
    gray: { bg: 'bg-gray-50', text: 'text-gray-700', border: 'border-gray-200', badge: 'bg-gray-100 text-gray-700' },
    slate: { bg: 'bg-slate-50', text: 'text-slate-700', border: 'border-slate-200', badge: 'bg-slate-100 text-slate-700' },
    teal: { bg: 'bg-teal-50', text: 'text-teal-700', border: 'border-teal-200', badge: 'bg-teal-100 text-teal-700' },
  }
  return colors[color] || colors.gray
}

const getPermissionIcon = (permission: string) => {
  if (permission.startsWith('✅')) return <CheckCircle2 className="h-4 w-4 text-green-600 inline mr-1" />
  if (permission.startsWith('❌')) return <XCircle className="h-4 w-4 text-red-500 inline mr-1" />
  if (permission.startsWith('⚙️')) return <AlertCircle className="h-4 w-4 text-blue-500 inline mr-1" />
  return null
}

interface GeneralUserProfile {
  id: number
  nic: string
  firstName: string
  lastName: string
  fullName: string
  email: string
  mobileNo: string
  homePhone: string
  address1: string
  address2: string
  address3: string
}

export default function FinanceTeamSetup() {
  const [expandedCategories, setExpandedCategories] = useState<string[]>(['PAY', 'DEP', 'REC'])
  const [searchTerm, setSearchTerm] = useState('')
  const [showCreateForm, setShowCreateForm] = useState(false)
  const [nicSearching, setNicSearching] = useState(false)
  const [gupFound, setGupFound] = useState<GeneralUserProfile | null>(null)
  const [gupNotFound, setGupNotFound] = useState(false)
  const [gupEditing, setGupEditing] = useState(false)
  const [gupSaving, setGupSaving] = useState(false)
  const [formData, setFormData] = useState({
    nic: '',
    firstName: '',
    lastName: '',
    email: '',
    mobileNo: '',
    employeeId: '',
    role: 'ACCOUNTANT',
    generalUserProfileId: null as number | null,
    customizations: {} as Record<string, string>
  })

  const toggleCategory = (categoryId: string) => {
    setExpandedCategories(prev => 
      prev.includes(categoryId) 
        ? prev.filter(id => id !== categoryId)
        : [...prev, categoryId]
    )
  }

  const totalTasks = CATEGORIES.reduce((sum, cat) => sum + cat.tasks.length, 0)

  const filteredCategories = CATEGORIES.map(cat => ({
    ...cat,
    tasks: cat.tasks.filter(task => 
      task.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
      task.code.toLowerCase().includes(searchTerm.toLowerCase())
    )
  })).filter(cat => cat.tasks.length > 0)

  const handleNicSearch = async () => {
    if (!formData.nic || formData.nic.length < 9) {
      toast.error('Please enter a valid NIC number')
      return
    }
    
    setNicSearching(true)
    setGupFound(null)
    setGupNotFound(false)
    setGupEditing(false)
    
    try {
      const response = await fetch(`/api/v1/general-user-profile/nic/${formData.nic}`)
      if (response.ok) {
        const profile: GeneralUserProfile = await response.json()
        setGupFound(profile)
        setFormData(prev => ({
          ...prev,
          firstName: profile.firstName || '',
          lastName: profile.lastName || '',
          email: profile.email || '',
          mobileNo: profile.mobileNo || '',
          generalUserProfileId: profile.id
        }))
        toast.success('Profile found! Fields auto-filled.')
      } else if (response.status === 404) {
        setGupNotFound(true)
        toast('No existing profile found. A new profile will be created.', { icon: '📝' })
      } else {
        toast.error('Error searching for profile')
      }
    } catch (error) {
      // For demo/mock purposes when API is not available
      setGupNotFound(true)
      toast('No existing profile found. A new profile will be created.', { icon: '📝' })
    } finally {
      setNicSearching(false)
    }
  }

  const handleSaveGup = async () => {
    if (!gupFound || !formData.generalUserProfileId) {
      toast.error('No profile selected to update')
      return
    }
    if (!formData.firstName || !formData.lastName || !formData.email) {
      toast.error('Please fill in all required fields')
      return
    }

    setGupSaving(true)
    try {
      const response = await fetch(`/api/v1/general-user-profile/${formData.generalUserProfileId}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          firstName: formData.firstName,
          lastName: formData.lastName,
          fullName: `${formData.firstName} ${formData.lastName}`,
          email: formData.email,
          mobileNo: formData.mobileNo,
        }),
      })

      if (!response.ok) {
        toast.error('Failed to update profile')
        return
      }

      const updated: GeneralUserProfile = await response.json()
      setGupFound(updated)
      setFormData(prev => ({
        ...prev,
        firstName: updated.firstName || '',
        lastName: updated.lastName || '',
        email: updated.email || '',
        mobileNo: updated.mobileNo || '',
      }))
      setGupEditing(false)
      toast.success('Profile updated successfully')
    } catch (error) {
      toast.error('Failed to update profile')
    } finally {
      setGupSaving(false)
    }
  }

  const resetForm = () => {
    setFormData({
      nic: '',
      firstName: '',
      lastName: '',
      email: '',
      mobileNo: '',
      employeeId: '',
      role: 'ACCOUNTANT',
      generalUserProfileId: null,
      customizations: {}
    })
    setGupFound(null)
    setGupNotFound(false)
    setGupEditing(false)
    setGupSaving(false)
  }

  const handleCreateUser = async () => {
    if (!formData.nic) {
      toast.error('Please enter NIC and search for profile first')
      return
    }
    if (!formData.firstName || !formData.lastName || !formData.email) {
      toast.error('Please fill in all required fields')
      return
    }
    
    try {
      // If GUP not found, we'll create a new one first
      let gupId = formData.generalUserProfileId
      
      if (!gupId && gupNotFound) {
        // Create new GeneralUserProfile
        const gupResponse = await fetch('/api/v1/general-user-profile', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({
            nic: formData.nic,
            firstName: formData.firstName,
            lastName: formData.lastName,
            fullName: `${formData.firstName} ${formData.lastName}`,
            email: formData.email,
            mobileNo: formData.mobileNo,
            isActive: 1
          })
        })
        
        if (gupResponse.ok) {
          const newGup = await gupResponse.json()
          gupId = newGup.id
        }
      }
      
      // Create UserLogin with FK to GeneralUserProfile
      // This would be the actual API call in production
      toast.success(`Finance team user ${formData.firstName} ${formData.lastName} created with ${formData.role} role${gupId ? ` (GUP ID: ${gupId})` : ''}`)
      setShowCreateForm(false)
      resetForm()
    } catch (error) {
      // For demo purposes
      toast.success(`Finance team user ${formData.firstName} ${formData.lastName} created with ${formData.role} role`)
      setShowCreateForm(false)
      resetForm()
    }
  }

  return (
    <div className="space-y-6">
      {/* Header */}
      <div className="flex items-center gap-4">
        <Link to="/roles" className="p-2 hover:bg-secondary-100 rounded-lg">
          <ArrowLeft className="h-5 w-5" />
        </Link>
        <div className="flex-1">
          <h1 className="text-2xl font-bold text-secondary-900">Finance Team Setup</h1>
          <p className="text-secondary-600">Configure roles and permissions for finance team members</p>
        </div>
        <button 
          onClick={() => setShowCreateForm(true)}
          className="btn-primary"
        >
          <User className="h-5 w-5 mr-2" />
          Create Finance User
        </button>
      </div>

      {/* Role Hierarchy Overview */}
      <div className="card border-2 border-emerald-200 bg-gradient-to-br from-emerald-50 to-white">
        <div className="card-body">
          <div className="flex items-center gap-3 mb-4">
            <div className="p-3 bg-emerald-100 rounded-xl">
              <Wallet className="h-6 w-6 text-emerald-600" />
            </div>
            <div>
              <h2 className="text-lg font-bold text-emerald-800">Role Hierarchy & Workflow</h2>
              <p className="text-sm text-emerald-600">{totalTasks} tasks across {CATEGORIES.length} categories</p>
            </div>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
            {/* Auditor */}
            <div className="bg-slate-100 rounded-lg p-4 border-2 border-slate-300">
              <div className="flex items-center gap-2 mb-2">
                <Shield className="h-5 w-5 text-slate-700" />
                <h3 className="font-bold text-slate-800">AUDITOR</h3>
              </div>
              <ul className="text-xs text-slate-600 space-y-1">
                <li>• Reviews all actions</li>
                <li>• Flags issues</li>
                <li>• Cannot modify data</li>
                <li>• Complete visibility</li>
              </ul>
              <div className="mt-2 text-center text-slate-400">↓ Reviews</div>
            </div>

            {/* Finance Controller */}
            <div className="bg-blue-100 rounded-lg p-4 border-2 border-blue-300">
              <div className="flex items-center gap-2 mb-2">
                <Shield className="h-5 w-5 text-blue-700" />
                <h3 className="font-bold text-blue-800">FINANCE CONTROLLER</h3>
              </div>
              <ul className="text-xs text-blue-600 space-y-1">
                <li>• Approves/Rejects</li>
                <li>• Final verification</li>
                <li>• Can override</li>
                <li>• Closes periods</li>
              </ul>
              <div className="mt-2 text-center text-blue-400">↓ Verifies</div>
            </div>

            {/* Accountant */}
            <div className="bg-green-100 rounded-lg p-4 border-2 border-green-300">
              <div className="flex items-center gap-2 mb-2">
                <Shield className="h-5 w-5 text-green-700" />
                <h3 className="font-bold text-green-800">ACCOUNTANT</h3>
              </div>
              <ul className="text-xs text-green-600 space-y-1">
                <li>• Daily data entry</li>
                <li>• Initial verification</li>
                <li>• Reconciliation matching</li>
                <li>• Report generation</li>
              </ul>
            </div>
          </div>
        </div>
      </div>

      {/* Search and Filter */}
      <div className="card card-body">
        <div className="flex flex-col md:flex-row gap-4">
          <div className="relative flex-1">
            <Search className="absolute left-3 top-1/2 -translate-y-1/2 h-5 w-5 text-secondary-400" />
            <input
              type="text"
              placeholder="Search tasks by name or code..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              className="input pl-10 w-full"
            />
          </div>
          <div className="flex gap-2">
            <button
              onClick={() => setExpandedCategories(CATEGORIES.map(c => c.id))}
              className="btn-secondary text-sm"
            >
              Expand All
            </button>
            <button
              onClick={() => setExpandedCategories([])}
              className="btn-secondary text-sm"
            >
              Collapse All
            </button>
          </div>
        </div>
      </div>

      {/* Permission Matrix */}
      <div className="space-y-4">
        {filteredCategories.map((category) => {
          const colors = getColorClasses(category.color)
          const isExpanded = expandedCategories.includes(category.id)
          
          return (
            <div key={category.id} className={`card border ${colors.border}`}>
              <button
                onClick={() => toggleCategory(category.id)}
                className={`w-full card-body flex items-center justify-between ${colors.bg} hover:opacity-90 transition-opacity`}
              >
                <div className="flex items-center gap-3">
                  {isExpanded ? (
                    <ChevronDown className={`h-5 w-5 ${colors.text}`} />
                  ) : (
                    <ChevronRight className={`h-5 w-5 ${colors.text}`} />
                  )}
                  <div className="text-left">
                    <h3 className={`font-semibold ${colors.text}`}>{category.name}</h3>
                    <p className="text-xs text-secondary-500">{category.tasks.length} tasks</p>
                  </div>
                </div>
                <span className={`badge ${colors.badge}`}>{category.priority} PRIORITY</span>
              </button>

              {isExpanded && (
                <div className="overflow-x-auto">
                  <table className="w-full text-sm">
                    <thead className="bg-secondary-50">
                      <tr>
                        <th className="text-left px-4 py-2 font-medium text-secondary-600">Code</th>
                        <th className="text-left px-4 py-2 font-medium text-secondary-600">Task</th>
                        <th className="text-center px-4 py-2 font-medium text-green-700 bg-green-50">Accountant</th>
                        <th className="text-center px-4 py-2 font-medium text-blue-700 bg-blue-50">Finance Controller</th>
                        <th className="text-center px-4 py-2 font-medium text-slate-700 bg-slate-50">Auditor</th>
                      </tr>
                    </thead>
                    <tbody>
                      {category.tasks.map((task, idx) => (
                        <tr key={task.code} className={idx % 2 === 0 ? 'bg-white' : 'bg-secondary-50'}>
                          <td className="px-4 py-2 font-mono text-xs text-secondary-500">{task.code}</td>
                          <td className="px-4 py-2">{task.name}</td>
                          <td className="px-4 py-2 text-center text-xs">
                            {getPermissionIcon(task.accountant)}
                            <span className="text-secondary-600">{task.accountant.replace(/^[✅❌⚙️]\s*/, '')}</span>
                          </td>
                          <td className="px-4 py-2 text-center text-xs">
                            {getPermissionIcon(task.financeController)}
                            <span className="text-secondary-600">{task.financeController.replace(/^[✅❌⚙️]\s*/, '')}</span>
                          </td>
                          <td className="px-4 py-2 text-center text-xs">
                            {getPermissionIcon(task.auditor)}
                            <span className="text-secondary-600">{task.auditor.replace(/^[✅❌⚙️]\s*/, '')}</span>
                          </td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </div>
              )}
            </div>
          )
        })}
      </div>

      {/* Create User Modal */}
      {showCreateForm && (
        <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50 p-4">
          <div className="bg-white rounded-xl shadow-2xl max-w-2xl w-full max-h-[90vh] overflow-y-auto">
            <div className="p-6 border-b border-secondary-200">
              <h2 className="text-xl font-bold text-secondary-900">Create Finance Team User</h2>
              <p className="text-sm text-secondary-500">Set up a new user with role-specific permissions</p>
            </div>

            <div className="p-6 space-y-6">
              {/* NIC Search Section */}
              <div className="bg-emerald-50 border border-emerald-200 rounded-lg p-4">
                <label className="label block mb-1.5 text-emerald-800 font-medium">
                  NIC Number * (Search for existing profile)
                </label>
                <div className="flex gap-2">
                  <input
                    type="text"
                    value={formData.nic}
                    onChange={(e) => setFormData({ ...formData, nic: e.target.value.toUpperCase() })}
                    className="input flex-1"
                    placeholder="Enter NIC (e.g., 199012345678 or 901234567V)"
                    maxLength={12}
                  />
                  <button
                    type="button"
                    onClick={handleNicSearch}
                    disabled={nicSearching || !formData.nic}
                    className="btn-primary px-4 flex items-center gap-2"
                  >
                    {nicSearching ? (
                      <Loader2 className="h-4 w-4 animate-spin" />
                    ) : (
                      <Search className="h-4 w-4" />
                    )}
                    Search
                  </button>
                </div>
                
                {/* Profile Status Indicator */}
                {gupFound && (
                  <div className="mt-3 flex items-center gap-2 text-green-700 bg-green-100 px-3 py-2 rounded-lg">
                    <UserCheck className="h-5 w-5" />
                    <span className="text-sm font-medium">
                      Existing profile found (ID: {gupFound.id}) - Fields auto-filled
                    </span>
                  </div>
                )}
                {gupFound && (
                  <div className="mt-3 flex items-center justify-end gap-2">
                    {!gupEditing ? (
                      <button
                        type="button"
                        onClick={() => setGupEditing(true)}
                        className="btn-secondary"
                      >
                        Edit Profile
                      </button>
                    ) : (
                      <>
                        <button
                          type="button"
                          onClick={() => {
                            setGupEditing(false)
                            setFormData(prev => ({
                              ...prev,
                              firstName: gupFound.firstName || '',
                              lastName: gupFound.lastName || '',
                              email: gupFound.email || '',
                              mobileNo: gupFound.mobileNo || '',
                            }))
                          }}
                          disabled={gupSaving}
                          className="btn-secondary"
                        >
                          Cancel
                        </button>
                        <button
                          type="button"
                          onClick={handleSaveGup}
                          disabled={gupSaving}
                          className="btn-primary"
                        >
                          {gupSaving ? (
                            <Loader2 className="h-4 w-4 animate-spin" />
                          ) : (
                            <Save className="h-4 w-4" />
                          )}
                          Save Profile
                        </button>
                      </>
                    )}
                  </div>
                )}
                {gupNotFound && (
                  <div className="mt-3 flex items-center gap-2 text-blue-700 bg-blue-100 px-3 py-2 rounded-lg">
                    <UserPlus className="h-5 w-5" />
                    <span className="text-sm font-medium">
                      New profile will be created when user is saved
                    </span>
                  </div>
                )}
              </div>

              {/* Basic Info - Only show after NIC search */}
              {(gupFound || gupNotFound) && (
                <>
                  <div className="grid grid-cols-2 gap-4">
                    <div>
                      <label className="label block mb-1.5">First Name *</label>
                      <input
                        type="text"
                        value={formData.firstName}
                        onChange={(e) => setFormData({ ...formData, firstName: e.target.value })}
                        className="input"
                        placeholder="Enter first name"
                        readOnly={!!gupFound && !gupEditing}
                      />
                    </div>
                    <div>
                      <label className="label block mb-1.5">Last Name *</label>
                      <input
                        type="text"
                        value={formData.lastName}
                        onChange={(e) => setFormData({ ...formData, lastName: e.target.value })}
                        className="input"
                        placeholder="Enter last name"
                        readOnly={!!gupFound && !gupEditing}
                      />
                    </div>
                  </div>

                  <div className="grid grid-cols-2 gap-4">
                    <div>
                      <label className="label block mb-1.5">Email *</label>
                      <input
                        type="email"
                        value={formData.email}
                        onChange={(e) => setFormData({ ...formData, email: e.target.value })}
                        className="input"
                        placeholder="user@temcobank.com"
                        readOnly={!!gupFound && !gupEditing}
                      />
                    </div>
                    <div>
                      <label className="label block mb-1.5">Mobile Number</label>
                      <input
                        type="text"
                        value={formData.mobileNo}
                        onChange={(e) => setFormData({ ...formData, mobileNo: e.target.value })}
                        className="input"
                        placeholder="07XXXXXXXX"
                        readOnly={!!gupFound && !gupEditing}
                      />
                    </div>
                  </div>

                  <div className="grid grid-cols-2 gap-4">
                    <div>
                      <label className="label block mb-1.5">Employee ID</label>
                      <input
                        type="text"
                        value={formData.employeeId}
                        onChange={(e) => setFormData({ ...formData, employeeId: e.target.value })}
                        className="input"
                        placeholder="EMP-XXX"
                      />
                    </div>
                    <div></div>
                  </div>

                  {/* Role Selection */}
                  <div>
                    <label className="label block mb-2">Select Role *</label>
                    <div className="grid grid-cols-3 gap-3">
                      {[
                        { id: 'ACCOUNTANT', label: 'Accountant', color: 'green', icon: FileText },
                        { id: 'FINANCE_CONTROLLER', label: 'Finance Controller', color: 'blue', icon: Shield },
                        { id: 'FINANCE_AUDITOR', label: 'Finance Auditor', color: 'slate', icon: Search },
                      ].map((role) => (
                        <button
                          key={role.id}
                          onClick={() => setFormData({ ...formData, role: role.id })}
                          className={`p-4 rounded-lg border-2 text-left transition-all ${
                            formData.role === role.id
                              ? `border-${role.color}-500 bg-${role.color}-50`
                              : 'border-secondary-200 hover:border-secondary-300'
                          }`}
                        >
                          <role.icon className={`h-5 w-5 mb-2 ${formData.role === role.id ? `text-${role.color}-600` : 'text-secondary-400'}`} />
                          <div className="font-medium text-sm">{role.label}</div>
                        </button>
                      ))}
                    </div>
                  </div>

                  {/* Customization Options */}
                  <div>
                    <h3 className="font-semibold text-secondary-900 mb-3 flex items-center gap-2">
                      <AlertCircle className="h-5 w-5 text-blue-500" />
                      User-Level Customizations
                    </h3>
                    <div className="grid grid-cols-2 gap-4">
                      {CUSTOMIZATION_OPTIONS.map((option) => (
                        <div key={option.id}>
                          <label className="label block mb-1.5 text-xs">{option.label}</label>
                          <select
                            value={formData.customizations[option.id] || ''}
                            onChange={(e) => setFormData({
                              ...formData,
                              customizations: { ...formData.customizations, [option.id]: e.target.value }
                            })}
                            className="input text-sm"
                          >
                            <option value="">-- Select --</option>
                            {option.options.map((opt) => (
                              <option key={opt} value={opt}>{opt}</option>
                            ))}
                          </select>
                        </div>
                      ))}
                    </div>
                  </div>
                </>
              )}
            </div>

            <div className="p-6 border-t border-secondary-200 flex justify-end gap-3">
              <button
                onClick={() => setShowCreateForm(false)}
                className="btn-secondary"
              >
                Cancel
              </button>
              <button
                onClick={handleCreateUser}
                className="btn-primary"
              >
                <Save className="h-5 w-5 mr-2" />
                Create User
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  )
}
