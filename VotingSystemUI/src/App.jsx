import { Routes, Route, useNavigate } from "react-router-dom"
import { useDispatch } from "react-redux"
import Navbar from "./components/Navbar"
import Home from "./pages/Home"
import Login from "./pages/Login"
import Signup from "./pages/Signup"
import Dashboard from "./pages/Dashboard"
import Elections from "./pages/Elections"
import Admin from "./pages/Admin"
import { ThemeProvider } from "./components/ThemeProvider"
import { useEffect } from "react"
import { login } from "./lib/features/auth/authSlice"

function App() {

  const dispatch = useDispatch();
  const navigate = useNavigate();

  useEffect(() => {

    const email = localStorage.getItem('Email');
    if (email) {
      const role = localStorage.getItem('Role');
      dispatch(login(email, role));
      navigate("/dashboard");
    }else {
      navigate("/login");

    }
  }, [])
  return (
    <ThemeProvider>
        <div className="min-h-screen bg-background transition-colors duration-300">
          <Navbar />
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/login" element={<Login />} />
            <Route path="/signup" element={<Signup />} />
            <Route path="/dashboard" element={<Dashboard />} />
            <Route path="/elections" element={<Elections />} />
            <Route path="/admin" element={<Admin />} />
          </Routes>
        </div>
    </ThemeProvider>
  )
}

export default App

