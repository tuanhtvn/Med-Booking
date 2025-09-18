import {
  useLocation,
  BrowserRouter as Router,
  Route,
  Routes,
} from "react-router-dom";
import Header from "./components/layouts/header";
import Footer from "./components/layouts/footer";
import Home from "./components/home";
import Login from "./components/users/login";
import Register from "./components/users/register";
import ForgotPassword from "./components/users/forgotPassword";
import UpdatePassword from "./components/users/updatePassword";
import ProtectedRoute from "./routes/protectedRoute";
import List from "./components/records/list";
import Booking from "./components/books/booking";
import Ticket from "./components/tickets/ticket";

function App() {
  return (
    <Router>
      <div className="App">
        <AppContent />
      </div>
    </Router>
  );
}
function AppContent() {
  const location = useLocation();

  const isHome =
    location.pathname === "/" || location.pathname.startsWith("/search");

  const isRecord = location.pathname.startsWith("/me/records");

  return (
    <>
      <Header isHome={isHome} isRecord={isRecord} />
      <div className="container container-fluid">
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/forgot-password" element={<ForgotPassword />} />
          <Route
            path="/"
            element={
              <ProtectedRoute>
                <Home />
              </ProtectedRoute>
            }
          />
          <Route
            path="/search/:keyword"
            element={
              <ProtectedRoute>
                <Home />
              </ProtectedRoute>
            }
          />
          <Route
            path="/update-password"
            element={
              <ProtectedRoute>
                <UpdatePassword />
              </ProtectedRoute>
            }
          />
          <Route
            path="/me/records"
            element={
              <ProtectedRoute>
                <List />
              </ProtectedRoute>
            }
          />
          <Route
            path="/book/:doctor"
            element={
              <ProtectedRoute>
                <Booking />
              </ProtectedRoute>
            }
          />
          <Route
            path="/me/records/:keyword"
            element={
              <ProtectedRoute>
                <List />
              </ProtectedRoute>
            }
          />
          <Route
            path="/me/tickets"
            element={
              <ProtectedRoute>
                <Ticket />
              </ProtectedRoute>
            }
          />
        </Routes>
      </div>
      <Footer />
    </>
  );
}

export default App;
