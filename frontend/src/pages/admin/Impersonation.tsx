import { useState, useEffect, useCallback } from 'react'
import { useNavigate } from 'react-router-dom'
import { Search, UserCog, AlertTriangle, Loader2, ChevronLeft, ChevronRight } from 'lucide-react'
import { useAuthStore } from '../../stores/authStore'
import { memberService, Member } from '../../api/memberService'
import { paginateMembers } from '../../data/members'
import toast from 'react-hot-toast'

const PAGE_SIZE = 20

export default function Impersonation() {
  const [searchTerm, setSearchTerm] = useState('')
  const [isLoading, setIsLoading] = useState<number | null>(null)
  const [members, setMembers] = useState<Member[]>([])
  const [isLoadingMembers, setIsLoadingMembers] = useState(true)
  const [useMockData, setUseMockData] = useState(false)
  const [page, setPage] = useState(0)
  const [totalPages, setTotalPages] = useState(1)
  const [totalElements, setTotalElements] = useState(0)
  const navigate = useNavigate()
  const { user, startImpersonation } = useAuthStore()

  const loadMembers = useCallback(async () => {
    setIsLoadingMembers(true)
    try {
      const response = await memberService.getMembers(page, PAGE_SIZE, searchTerm)
      setMembers(response.content)
      setTotalPages(response.totalPages)
      setTotalElements(response.totalElements)
      setUseMockData(false)
    } catch (error) {
      console.error('Failed to load members from API, using local data:', error)
      // Use indexed local data with pagination
      const result = paginateMembers(page, PAGE_SIZE, searchTerm)
      setMembers(result.content)
      setTotalElements(result.totalElements)
      setTotalPages(result.totalPages)
      setUseMockData(true)
    } finally {
      setIsLoadingMembers(false)
    }
  }, [page, searchTerm])

  useEffect(() => {
    loadMembers()
  }, [loadMembers])

  useEffect(() => {
    const debounce = setTimeout(() => {
      if (page !== 0) setPage(0)
    }, 300)
    return () => clearTimeout(debounce)
  }, [searchTerm])

  const getDisplayName = (member: Member) => {
    if (member.fullName) return member.fullName
    if (member.firstName || member.lastName) return `${member.firstName || ''} ${member.lastName || ''}`.trim()
    return 'Unknown'
  }

  const getInitials = (member: Member) => {
    const name = getDisplayName(member)
    return name.split(' ').map(n => n[0]).join('').substring(0, 2).toUpperCase()
  }

  const handleImpersonate = async (member: Member) => {
    setIsLoading(member.id)
    try {
      const displayName = getDisplayName(member)
      
      // Store impersonation info for audit trail
      startImpersonation(
        { id: member.id, username: member.email || member.membershipNo, fullName: displayName, email: member.email || '', role: 'MEMBER', permissions: ['member.*'] },
        user!
      )
      
      // Build impersonation URL for Customer Portal Dashboard (my.temcobank.com/dashboard)
      const portalUrl = 'https://my.temcobank.com/dashboard'
      const impersonationParams = new URLSearchParams({
        impersonate: 'true',
        memberId: member.id.toString(),
        memberNo: member.membershipNo,
        email: member.email || '',
        name: displayName,
        adminId: user?.id?.toString() || '',
        adminUser: user?.username || '',
        ts: Date.now().toString()
      })
      
      toast.success(`Opening ${displayName}'s Dashboard`)
      
      // Open member's personal dashboard in Customer Portal
      window.open(`${portalUrl}?${impersonationParams.toString()}`, '_blank')
    } catch (error) {
      toast.error('Failed to start impersonation')
    } finally {
      setIsLoading(null)
    }
  }

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-secondary-900">User Impersonation</h1>
        <p className="text-secondary-600">Login as a customer to assist with their account</p>
      </div>

      <div className="card card-body bg-amber-50 border-amber-200">
        <div className="flex items-start gap-3">
          <AlertTriangle className="h-5 w-5 text-amber-600 flex-shrink-0 mt-0.5" />
          <div>
            <h4 className="font-semibold text-amber-800">Important Notice</h4>
            <p className="text-sm text-amber-700 mt-1">
              All actions performed during impersonation are logged and audited. Use this feature responsibly and only for legitimate support purposes.
            </p>
          </div>
        </div>
      </div>

      <div className="card card-body">
        <div className="relative max-w-md">
          <Search className="absolute left-3 top-1/2 -translate-y-1/2 h-5 w-5 text-secondary-400" />
          <input
            type="text"
            placeholder="Search by name, email, or member number..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className="input pl-10"
          />
        </div>
      </div>

      {/* Mock Data Banner */}
      {useMockData && !isLoadingMembers && (
        <div className="bg-yellow-50 border border-yellow-200 rounded-lg p-3 text-yellow-800 text-sm">
          ⚠️ Backend unavailable - showing mock data
        </div>
      )}

      {/* Loading State */}
      {isLoadingMembers ? (
        <div className="flex justify-center py-12">
          <Loader2 className="h-8 w-8 animate-spin text-blue-600" />
        </div>
      ) : (
        <div className="card overflow-hidden">
          <div className="overflow-x-auto">
            <table className="w-full">
              <thead className="bg-secondary-50 border-b border-secondary-200">
                <tr>
                  <th className="px-6 py-3 text-left text-xs font-semibold text-secondary-600 uppercase">Member</th>
                  <th className="px-6 py-3 text-left text-xs font-semibold text-secondary-600 uppercase">Member No</th>
                  <th className="px-6 py-3 text-left text-xs font-semibold text-secondary-600 uppercase">Email</th>
                  <th className="px-6 py-3 text-left text-xs font-semibold text-secondary-600 uppercase">Status</th>
                  <th className="px-6 py-3 text-right text-xs font-semibold text-secondary-600 uppercase">Action</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-secondary-100">
                {members.map(member => (
                  <tr key={member.id} className="hover:bg-secondary-50">
                    <td className="px-6 py-4">
                      <div className="flex items-center">
                        <div className="h-10 w-10 rounded-full bg-primary-100 flex items-center justify-center">
                          <span className="text-primary-700 font-semibold">{getInitials(member)}</span>
                        </div>
                        <div className="ml-4">
                          <p className="font-medium text-secondary-900">{getDisplayName(member)}</p>
                          <p className="text-sm text-secondary-500">{member.nic}</p>
                        </div>
                      </div>
                    </td>
                    <td className="px-6 py-4 text-sm font-mono text-secondary-600">{member.membershipNo}</td>
                    <td className="px-6 py-4 text-sm text-secondary-600">{member.email || '-'}</td>
                    <td className="px-6 py-4">
                      <span className={`badge ${member.isActive ? 'badge-success' : 'badge-danger'}`}>{member.isActive ? 'active' : 'inactive'}</span>
                    </td>
                    <td className="px-6 py-4 text-right">
                      <button
                        onClick={() => handleImpersonate(member)}
                        disabled={isLoading !== null || !member.isActive}
                        className="btn-primary btn-sm"
                      >
                        {isLoading === member.id ? (
                          <><Loader2 className="h-4 w-4 mr-1 animate-spin" />Starting...</>
                        ) : (
                          <><UserCog className="h-4 w-4 mr-1" />Impersonate</>
                        )}
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>

          {/* Pagination */}
          <div className="px-6 py-4 border-t border-secondary-200 flex items-center justify-between">
            <p className="text-sm text-secondary-600">
              Showing {members.length} of {totalElements} members
            </p>
            <div className="flex items-center gap-2">
              <button
                onClick={() => setPage(p => Math.max(0, p - 1))}
                disabled={page === 0}
                className="p-2 rounded-lg hover:bg-secondary-100 disabled:opacity-50 disabled:cursor-not-allowed"
              >
                <ChevronLeft className="h-5 w-5" />
              </button>
              <span className="text-sm text-secondary-600">
                Page {page + 1} of {Math.max(1, totalPages)}
              </span>
              <button
                onClick={() => setPage(p => Math.min(totalPages - 1, p + 1))}
                disabled={page >= totalPages - 1}
                className="p-2 rounded-lg hover:bg-secondary-100 disabled:opacity-50 disabled:cursor-not-allowed"
              >
                <ChevronRight className="h-5 w-5" />
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  )
}
