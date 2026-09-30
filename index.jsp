<%@ page import="java.util.*" %>
<%@ page import="com.library.model.*" %>
<%
List<Book> books=(List<Book>)request.getAttribute("books");
List<Book> availableBooks=(List<Book>)request.getAttribute("availableBooks");
List<Member> members=(List<Member>)request.getAttribute("members");
List<IssuedBook> issues=(List<IssuedBook>)request.getAttribute("issues");
List<Payment> payments=(List<Payment>)request.getAttribute("payments");
String error=(String)request.getAttribute("error");
%>
<!doctype html><html><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width,initial-scale=1">
<title>LibraNova — Library Management</title><link rel="stylesheet" href="style.css"></head><body>
<header><div><div class="logo">📚 LibraNova</div><h1>Smart Library Management System</h1><p>Java • JDBC • MySQL</p></div><div class="badge">● SYSTEM ONLINE</div></header>
<nav><a href="#dashboard">Dashboard</a><a href="#books">Books</a><a href="#members">Members</a><a href="#issue">Issue/Return</a><a href="#payments">Payments</a></nav>
<main>
<%if(error!=null){%><div class="error">⚠ <%=error%></div><%}%>
<section id="dashboard" class="dashboard">
<div class="stat"><span>📚</span><b><%=books.size()%></b><small>Total Books</small></div>
<div class="stat"><span>👥</span><b><%=members.size()%></b><small>Members</small></div>
<div class="stat"><span>✅</span><b><%=availableBooks.size()%></b><small>Books Available</small></div>
<div class="stat"><span>💳</span><b><%=payments.size()%></b><small>Payments</small></div>
</section>

<section id="books" class="card"><h2>📖 Add Book</h2><form method="post" action="library" class="grid">
<input type="hidden" name="action" value="addBook"><input name="title" placeholder="Book title" required>
<input name="author" placeholder="Author" required><input name="category" placeholder="Category">
<input name="isbn" placeholder="ISBN"><input type="number" name="quantity" min="1" value="1" required><button>Add Book</button>
</form></section>

<section class="card"><h2>Book Inventory</h2><input id="bookSearch" onkeyup="filterTable('bookSearch','bookTable')" placeholder="🔎 Search books...">
<div class="table-wrap"><table id="bookTable"><tr><th>ID</th><th>Title</th><th>Author</th><th>Category</th><th>Available</th><th>Action</th></tr>
<%for(Book b:books){%><tr><td><%=b.getId()%></td><td><%=b.getTitle()%></td><td><%=b.getAuthor()%></td><td><%=b.getCategory()%></td><td><span class="pill"><%=b.getAvailable()%>/<%=b.getQuantity()%></span></td><td><form method="post" action="library"><input type="hidden" name="action" value="deleteBook"><input type="hidden" name="id" value="<%=b.getId()%>"><button class="danger">Delete</button></form></td></tr><%}%>
</table></div></section>

<section id="members" class="card"><h2>👤 Add Member</h2><form method="post" action="library" class="grid">
<input type="hidden" name="action" value="addMember"><input name="name" placeholder="Member name" required>
<input type="email" name="email" placeholder="Email" required><input name="phone" placeholder="Phone">
<select name="membershipType"><option>Standard</option><option>Premium</option><option>Student</option></select><button>Add Member</button>
</form></section>

<section class="card"><h2>Members</h2><div class="table-wrap"><table><tr><th>ID</th><th>Name</th><th>Email</th><th>Phone</th><th>Plan</th><th>Action</th></tr>
<%for(Member m:members){%><tr><td><%=m.getId()%></td><td><%=m.getName()%></td><td><%=m.getEmail()%></td><td><%=m.getPhone()%></td><td><span class="pill"><%=m.getMembershipType()%></span></td><td><form method="post" action="library"><input type="hidden" name="action" value="deleteMember"><input type="hidden" name="id" value="<%=m.getId()%>"><button class="danger">Delete</button></form></td></tr><%}%>
</table></div></section>

<section id="issue" class="card"><h2>🔄 Issue / Return</h2><form method="post" action="library" class="grid">
<input type="hidden" name="action" value="issueBook"><select name="bookId" required><option value="">Select available book</option>
<%for(Book b:availableBooks){%><option value="<%=b.getId()%>"><%=b.getTitle()%> — <%=b.getAvailable()%> available</option><%}%></select>
<select name="memberId" required><option value="">Select member</option><%for(Member m:members){%><option value="<%=m.getId()%>"><%=m.getName()%></option><%}%></select>
<input type="date" name="dueDate" required><button>Issue Book</button></form>
<div class="table-wrap"><table><tr><th>Book</th><th>Member</th><th>Issued</th><th>Due</th><th>Status</th><th>Fine</th><th>Action</th></tr>
<%for(IssuedBook i:issues){%><tr><td><%=i.getBookTitle()%></td><td><%=i.getMemberName()%></td><td><%=i.getIssueDate()%></td><td><%=i.getDueDate()%></td><td><span class="status"><%=i.getStatus()%></span></td><td>₹<%=String.format("%.2f",i.getFine())%></td><td><%if("ISSUED".equals(i.getStatus())){%><form method="post" action="library"><input type="hidden" name="action" value="returnBook"><input type="hidden" name="issueId" value="<%=i.getId()%>"><button>Return</button></form><%}else{%>Returned<%}%></td></tr><%}%>
</table></div></section>

<section id="payments" class="card payment-card"><h2>💳 Library Payments</h2><p class="muted">Record membership fees, fines or other library payments.</p>
<form method="post" action="library" class="grid">
<input type="hidden" name="action" value="addPayment"><select name="memberId" required><option value="">Select member</option><%for(Member m:members){%><option value="<%=m.getId()%>"><%=m.getName()%></option><%}%></select>
<input type="number" name="amount" min="1" step="0.01" placeholder="Amount ₹" required>
<select name="paymentType"><option>MEMBERSHIP</option><option>FINE</option><option>OTHER</option></select>
<select name="paymentMethod" required><option value="">Payment method</option><option value="CASH">💵 Cash</option><option value="UPI">📱 UPI</option><option value="CARD">💳 Card</option><option value="NET_BANKING">🏦 Net Banking</option></select>
<input name="transactionRef" placeholder="Transaction reference (optional)"><button class="pay">Record Payment</button>
</form>
<div class="table-wrap"><table><tr><th>Member</th><th>Amount</th><th>Type</th><th>Method</th><th>Reference</th><th>Status</th><th>Date</th></tr>
<%for(Payment p:payments){%><tr><td><%=p.getMemberName()%></td><td>₹<%=String.format("%.2f",p.getAmount())%></td><td><%=p.getPaymentType()%></td><td><%=p.getPaymentMethod()%></td><td><%=p.getTransactionRef()==null?"-":p.getTransactionRef()%></td><td><span class="status"><%=p.getStatus()%></span></td><td><%=p.getPaymentDate()%></td></tr><%}%>
</table></div></section>
</main><footer>© 2026 LibraNova • Built with Java JDBC + MySQL</footer>
<script src="script.js"></script></body></html>
