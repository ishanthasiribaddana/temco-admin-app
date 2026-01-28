const http = require('http');

const PORT = 8080;

// Mock data
const users = {
  admin: { password: 'Admin@123', userId: 1, fullName: 'System Administrator', email: 'admin@temcobank.lk', roles: ['ADMIN'] },
  cashier: { password: 'Cashier@123', userId: 2, fullName: 'John Cashier', email: 'cashier@temcobank.lk', roles: ['CASHIER'] }
};

const customers = [
  { id: 1, studentId: 'STU001', nic: '200012345678', firstName: 'Kamal', lastName: 'Perera', fullName: 'Kamal Perera', email: 'kamal@email.com', mobileNo: '0771234567', dateOfBirth: '2000-05-15', customerStatus: 'ACTIVE', registrationDate: '2024-01-15', bankName: 'BOC', enrollmentCount: 2, outstandingDueCount: 1 },
  { id: 2, studentId: 'STU002', nic: '199987654321', firstName: 'Nimal', lastName: 'Silva', fullName: 'Nimal Silva', email: 'nimal@email.com', mobileNo: '0779876543', dateOfBirth: '1999-08-20', customerStatus: 'ACTIVE', registrationDate: '2024-02-10', bankName: 'HNB', enrollmentCount: 1, outstandingDueCount: 0 },
  { id: 3, studentId: 'STU003', nic: '200156789012', firstName: 'Sunil', lastName: 'Fernando', fullName: 'Sunil Fernando', email: 'sunil@email.com', mobileNo: '0712345678', dateOfBirth: '2001-03-10', customerStatus: 'ACTIVE', registrationDate: '2024-03-05', bankName: 'Commercial Bank', enrollmentCount: 3, outstandingDueCount: 2 },
  { id: 4, studentId: 'STU004', nic: '199812340987', firstName: 'Kumari', lastName: 'Jayawardena', fullName: 'Kumari Jayawardena', email: 'kumari@email.com', mobileNo: '0761234567', dateOfBirth: '1998-11-25', customerStatus: 'SUSPENDED', registrationDate: '2023-12-01', bankName: 'Sampath Bank', enrollmentCount: 1, outstandingDueCount: 3 },
  { id: 5, studentId: 'STU005', nic: '200234567890', firstName: 'Ruwan', lastName: 'Wickramasinghe', fullName: 'Ruwan Wickramasinghe', email: 'ruwan@email.com', mobileNo: '0751234567', dateOfBirth: '2002-07-08', customerStatus: 'ACTIVE', registrationDate: '2024-04-20', bankName: 'NSB', enrollmentCount: 2, outstandingDueCount: 1 },
];

// Helper to parse JSON body
const parseBody = (req) => new Promise((resolve) => {
  let body = '';
  req.on('data', chunk => body += chunk);
  req.on('end', () => {
    try { resolve(JSON.parse(body)); } 
    catch { resolve({}); }
  });
});

// CORS headers
const corsHeaders = {
  'Access-Control-Allow-Origin': '*',
  'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
  'Access-Control-Allow-Headers': 'Content-Type, Authorization',
  'Content-Type': 'application/json'
};

const server = http.createServer(async (req, res) => {
  const url = req.url.replace('/temco-bank/api/v1', '');
  
  // Handle CORS preflight
  if (req.method === 'OPTIONS') {
    res.writeHead(200, corsHeaders);
    return res.end();
  }

  console.log(`${req.method} ${url}`);

  // Auth endpoints
  if (url === '/auth/login' && req.method === 'POST') {
    const body = await parseBody(req);
    const user = users[body.username];
    
    if (user && user.password === body.password) {
      res.writeHead(200, corsHeaders);
      return res.end(JSON.stringify({
        accessToken: 'mock-jwt-token-' + Date.now(),
        refreshToken: 'mock-refresh-token-' + Date.now(),
        userId: user.userId,
        username: body.username,
        fullName: user.fullName,
        email: user.email,
        roles: user.roles,
        mustChangePassword: false
      }));
    }
    res.writeHead(401, corsHeaders);
    return res.end(JSON.stringify({ message: 'Invalid username or password' }));
  }

  if (url === '/auth/me' && req.method === 'GET') {
    res.writeHead(200, corsHeaders);
    return res.end(JSON.stringify({
      userId: 1,
      username: 'admin',
      fullName: 'System Administrator',
      email: 'admin@temcobank.lk',
      roles: ['ADMIN'],
      mustChangePassword: false
    }));
  }

  if (url === '/auth/logout' && req.method === 'POST') {
    res.writeHead(200, corsHeaders);
    return res.end(JSON.stringify({ message: 'Logged out successfully' }));
  }

  // Customer endpoints
  if (url.match(/^\/customers(\?|$)/) && req.method === 'GET') {
    res.writeHead(200, corsHeaders);
    return res.end(JSON.stringify({
      content: customers,
      page: 0,
      size: 20,
      totalElements: customers.length,
      totalPages: 1,
      first: true,
      last: true
    }));
  }

  if (url.match(/^\/customers\/\d+$/) && req.method === 'GET') {
    const id = parseInt(url.split('/').pop());
    const customer = customers.find(c => c.id === id);
    if (customer) {
      res.writeHead(200, corsHeaders);
      return res.end(JSON.stringify(customer));
    }
    res.writeHead(404, corsHeaders);
    return res.end(JSON.stringify({ message: 'Customer not found' }));
  }

  // Dashboard endpoints
  if (url === '/dashboard/summary') {
    res.writeHead(200, corsHeaders);
    return res.end(JSON.stringify({
      totalCustomers: 1250,
      activeEnrollments: 890,
      pendingPayments: 156,
      overduePayments: 23,
      todayCollections: 125000,
      monthlyCollections: 2450000
    }));
  }

  // Default 404
  res.writeHead(404, corsHeaders);
  res.end(JSON.stringify({ message: 'Not found' }));
});

server.listen(PORT, () => {
  console.log(`\n🚀 Mock API Server running at http://localhost:${PORT}`);
  console.log(`\n📋 Available endpoints:`);
  console.log(`   POST /temco-bank/api/v1/auth/login`);
  console.log(`   GET  /temco-bank/api/v1/auth/me`);
  console.log(`   POST /temco-bank/api/v1/auth/logout`);
  console.log(`   GET  /temco-bank/api/v1/customers`);
  console.log(`   GET  /temco-bank/api/v1/customers/:id`);
  console.log(`\n🔐 Login credentials:`);
  console.log(`   admin / Admin@123`);
  console.log(`   cashier / Cashier@123`);
  console.log(`\nPress Ctrl+C to stop\n`);
});
