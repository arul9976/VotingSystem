"use client"

import { useState, useEffect } from "react"
import { Link, useLocation, useNavigate } from "react-router-dom"
import { useDispatch, useSelector } from "react-redux"
import { ModeToggle } from "./ModeToggle"
import { logout } from "../lib/features/auth/authSlice"

export default function Navbar() {
  const [isMenuOpen, setIsMenuOpen] = useState(false)
  const location = useLocation()
  const isAuthenticated = useSelector((state) => state.auth.isAuthenticated)
  const userRole = useSelector((state) => state.auth.userRole)
  const dispatch = useDispatch()
  const navigate = useNavigate();
  const toggleMenu = () => {
    setIsMenuOpen(!isMenuOpen)
  }

  useEffect(() => {
    setIsMenuOpen(false)
  }, [location.pathname])

  return (
    <header className="sticky top-0 z-50 w-full border-b bg-background/95 backdrop-blur supports-[backdrop-filter]:bg-background/60">
      <div className="container flex h-16 items-center justify-between">
        <div className="flex items-center gap-2">
          <Link to="/" className="flex items-center gap-2">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="24"
              height="24"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              strokeWidth="2"
              strokeLinecap="round"
              strokeLinejoin="round"
              className="h-6 w-6"
            >
              <path d="m18 6-6-4-6 4"></path>
              <path d="M18 10v8a2 2 0 0 1-2 2H8a2 2 0 0 1-2-2v-8"></path>
              <path d="M12 10v6"></path>
              <path d="m15 12-3-2-3 2"></path>
            </svg>
            <span className="font-bold">VoteSystem</span>
          </Link>
        </div>
        <nav className="hidden md:flex items-center gap-6">
          <Link
            to="/"
            className={`text-sm font-medium transition-colors hover:text-primary ${location.pathname === "/" ? "text-primary" : "text-muted-foreground"
              }`}
          >
            Home
          </Link>
          {isAuthenticated ? (
            <>
              <Link
                to="/dashboard"
                className={`text-sm font-medium transition-colors hover:text-primary ${location.pathname === "/dashboard" ? "text-primary" : "text-muted-foreground"
                  }`}
              >
                Dashboard
              </Link>
              <Link
                to="/elections"
                className={`text-sm font-medium transition-colors hover:text-primary ${location.pathname === "/elections" ? "text-primary" : "text-muted-foreground"
                  }`}
              >
                Elections
              </Link>
              {userRole === "admin" && (
                <Link
                  to="/admin"
                  className={`text-sm font-medium transition-colors hover:text-primary ${location.pathname.startsWith("/admin") ? "text-primary" : "text-muted-foreground"
                    }`}
                >
                  Admin
                </Link>
              )}
            </>
          ) : (
            <>
              <Link
                to="/login"
                className={`text-sm font-medium transition-colors hover:text-primary ${location.pathname === "/login" ? "text-primary" : "text-muted-foreground"
                  }`}
              >
                Login
              </Link>
              <Link
                to="/signup"
                className={`text-sm font-medium transition-colors hover:text-primary ${location.pathname === "/signup" ? "text-primary" : "text-muted-foreground"
                  }`}
              >
                Sign Up
              </Link>
            </>
          )}
        </nav>
        <div className="flex items-center gap-2">
          <ModeToggle />
          {isAuthenticated && (
            <button onClick={() => {
              console.log("USHUWHUSHUS");
              localStorage.clear()
              dispatch(logout())
              navigate("/");

            }} className="hidden md:flex h-10 w-10 items-center justify-center rounded-md hover:bg-accent">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="24"
                height="24"
                viewBox="0 0 24 24"
                fill="none"
                stroke="currentColor"
                strokeWidth="2"
                strokeLinecap="round"
                strokeLinejoin="round"
                className="h-5 w-5"
              >
                <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"></path>
                <polyline points="16 17 21 12 16 7"></polyline>
                <line x1="21" y1="12" x2="9" y2="12"></line>
              </svg>
              <span className="sr-only">Logout</span>
            </button>
          )}
          <button
            className="md:hidden h-10 w-10 items-center justify-center rounded-md hover:bg-accent"
            onClick={toggleMenu}
          >
            {isMenuOpen ? (
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="24"
                height="24"
                viewBox="0 0 24 24"
                fill="none"
                stroke="currentColor"
                strokeWidth="2"
                strokeLinecap="round"
                strokeLinejoin="round"
                className="h-5 w-5"
              >
                <line x1="18" y1="6" x2="6" y2="18"></line>
                <line x1="6" y1="6" x2="18" y2="18"></line>
              </svg>
            ) : (
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="24"
                height="24"
                viewBox="0 0 24 24"
                fill="none"
                stroke="currentColor"
                strokeWidth="2"
                strokeLinecap="round"
                strokeLinejoin="round"
                className="h-5 w-5"
              >
                <line x1="4" y1="12" x2="20" y2="12"></line>
                <line x1="4" y1="6" x2="20" y2="6"></line>
                <line x1="4" y1="18" x2="20" y2="18"></line>
              </svg>
            )}
            <span className="sr-only">Toggle menu</span>
          </button>
        </div>
      </div>
      {isMenuOpen && (
        <div className="container md:hidden py-4 animate-in slide-in-from-top-5">
          <nav className="flex flex-col gap-4">
            <Link
              to="/"
              className={`text-sm font-medium transition-colors hover:text-primary ${location.pathname === "/" ? "text-primary" : "text-muted-foreground"
                }`}
            >
              Home
            </Link>
            {isAuthenticated ? (
              <>
                <Link
                  to="/dashboard"
                  className={`text-sm font-medium transition-colors hover:text-primary ${location.pathname === "/dashboard" ? "text-primary" : "text-muted-foreground"
                    }`}
                >
                  Dashboard
                </Link>
                <Link
                  to="/elections"
                  className={`text-sm font-medium transition-colors hover:text-primary ${location.pathname === "/elections" ? "text-primary" : "text-muted-foreground"
                    }`}
                >
                  Elections
                </Link>
                {userRole === "admin" && (
                  <Link
                    to="/admin"
                    className={`text-sm font-medium transition-colors hover:text-primary ${location.pathname.startsWith("/admin") ? "text-primary" : "text-muted-foreground"
                      }`}
                  >
                    Admin
                  </Link>
                )}
                <button onClick={() => {
                  console.log("USHUWHUSHUS");

                  logout();
                  navigate("/");

                }} className="flex items-center text-sm font-medium text-muted-foreground hover:text-primary">
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    width="24"
                    height="24"
                    viewBox="0 0 24 24"
                    fill="none"
                    stroke="currentColor"
                    strokeWidth="2"
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    className="h-5 w-5 mr-2"
                  >
                    <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"></path>
                    <polyline points="16 17 21 12 16 7"></polyline>
                    <line x1="21" y1="12" x2="9" y2="12"></line>
                  </svg>
                  Logout1
                </button>
              </>
            ) : (
              <>
                <Link
                  to="/login"
                  className={`text-sm font-medium transition-colors hover:text-primary ${location.pathname === "/login" ? "text-primary" : "text-muted-foreground"
                    }`}
                >
                  Login
                </Link>
                <Link
                  to="/signup"
                  className={`text-sm font-medium transition-colors hover:text-primary ${location.pathname === "/signup" ? "text-primary" : "text-muted-foreground"
                    }`}
                >
                  Sign Up
                </Link>
              </>
            )}
          </nav>
        </div>
      )}
    </header>
  )
}

