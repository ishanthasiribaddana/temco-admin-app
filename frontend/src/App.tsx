import { Routes, Route, Navigate } from 'react-router-dom'
import { useAuthStore } from './stores/authStore'
import AdminLayout from './layouts/AdminLayout'
import Login from './pages/auth/Login'
import ForgotPassword from './pages/auth/ForgotPassword'
import ChangePassword from './pages/auth/ChangePassword'
import Dashboard from './pages/Dashboard'
import UserList from './pages/users/UserList'
import UserCreate from './pages/users/UserCreate'
import UserEdit from './pages/users/UserEdit'
import RoleList from './pages/roles/RoleList'
import RoleCreate from './pages/roles/RoleCreate'
import RoleEdit from './pages/roles/RoleEdit'
import FinanceTeamSetup from './pages/roles/FinanceTeamSetup'
import PermissionMatrix from './pages/permissions/PermissionMatrix'
import MenuManagement from './pages/menus/MenuManagement'
import Settings from './pages/settings/Settings'
import AuditLogs from './pages/audit/AuditLogs'
import DataChangeLogs from './pages/audit/DataChangeLogs'
import ReferenceData from './pages/reference/ReferenceData'
import Impersonation from './pages/admin/Impersonation'
import Profile from './pages/profile/Profile'
import EmailCompose from './pages/email/EmailCompose'
import LoanApplications from './pages/loans/LoanApplications'
import ActiveLoans from './pages/loans/ActiveLoans'
import LoanPayments from './pages/loans/LoanPayments'
import LoanDefaults from './pages/loans/LoanDefaults'
import FinancialReports from './pages/reports/FinancialReports'
import UserActivityReport from './pages/reports/UserActivityReport'
import LoanStatistics from './pages/reports/LoanStatistics'
import NotificationCenter from './pages/notifications/NotificationCenter'
import NotificationTemplates from './pages/notifications/NotificationTemplates'
import DeveloperGuide from './pages/developer/DeveloperGuide'
import CICDGuide from './pages/developer/CICDGuide'

function ProtectedRoute({ children }: { children: React.ReactNode }) {
  const { isAuthenticated } = useAuthStore()
  
  if (!isAuthenticated) {
    return <Navigate to="/login" replace />
  }
  
  return <>{children}</>
}

function App() {
  return (
    <Routes>
      {/* Public Routes */}
      <Route path="/login" element={<Login />} />
      <Route path="/forgot-password" element={<ForgotPassword />} />
      <Route path="/change-password" element={<ChangePassword />} />
      
      {/* Protected Routes */}
      <Route
        path="/"
        element={
          <ProtectedRoute>
            <AdminLayout />
          </ProtectedRoute>
        }
      >
        <Route index element={<Dashboard />} />
        
        {/* User Management */}
        <Route path="users" element={<UserList />} />
        <Route path="users/create" element={<UserCreate />} />
        <Route path="users/:id/edit" element={<UserEdit />} />
        
        {/* Role Management */}
        <Route path="roles" element={<RoleList />} />
        <Route path="roles/create" element={<RoleCreate />} />
        <Route path="roles/:id/edit" element={<RoleEdit />} />
        <Route path="roles/finance-team" element={<FinanceTeamSetup />} />
        
        {/* Permissions */}
        <Route path="permissions" element={<PermissionMatrix />} />
        
        {/* Menu Management */}
        <Route path="menus" element={<MenuManagement />} />
        
        {/* Settings */}
        <Route path="settings" element={<Settings />} />
        
        {/* Audit */}
        <Route path="audit/activity" element={<AuditLogs />} />
        <Route path="audit/data-changes" element={<DataChangeLogs />} />
        
        {/* Reference Data */}
        <Route path="reference/:category?" element={<ReferenceData />} />
        
        {/* Admin Tools */}
        <Route path="impersonation" element={<Impersonation />} />
        
        {/* Loan Management */}
        <Route path="loans/applications" element={<LoanApplications />} />
        <Route path="loans/active" element={<ActiveLoans />} />
        <Route path="loans/payments" element={<LoanPayments />} />
        <Route path="loans/defaults" element={<LoanDefaults />} />
        
        {/* Reports */}
        <Route path="reports/financial" element={<FinancialReports />} />
        <Route path="reports/user-activity" element={<UserActivityReport />} />
        <Route path="reports/loan-stats" element={<LoanStatistics />} />
        
        {/* Notifications */}
        <Route path="notifications" element={<NotificationCenter />} />
        <Route path="notifications/templates" element={<NotificationTemplates />} />
        
        {/* Email */}
        <Route path="email" element={<EmailCompose />} />
        
        {/* Profile */}
        <Route path="profile" element={<Profile />} />
        
        {/* Developer Guide */}
        <Route path="developer-guide" element={<DeveloperGuide />} />
        <Route path="developer-guide/cicd" element={<CICDGuide />} />
      </Route>
      
      {/* Catch all */}
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  )
}

export default App
