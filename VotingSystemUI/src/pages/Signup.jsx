"use client"

import { useState } from "react"
import { Link, useNavigate } from "react-router-dom"
import { Button } from "../components/Button"
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "../components/Card"
import { Input } from "../components/Input"
import { Label } from "../components/Label"
import { registerVoter } from "../Fetch/voterFetch"

export default function Signup() {

  const [isLoading, setIsLoading] = useState(false)
  const [error, setError] = useState("")

  const [user, setUser] = useState({
    name: "",
    email: "",
    password: "",
    confirmPassword: "",
    age: 18,
    role: "voter",
    voterId: "",
    isEligible: true
  })

  const validateForm = (user) => {
    let errors = {};

    if (!user.name) {
      errors.name = "Name is required";
    }

    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if (!user.email) {
      errors.email = "Email is required";
    } else if (!emailRegex.test(user.email)) {
      errors.email = "Please enter a valid email address";
    }

    if (!user.password) {
      errors.password = "Password is required";
    }
    if (!user.confirmPassword) {
      errors.confirmPassword = "Please confirm your password";
    } else if (user.password !== user.confirmPassword) {
      errors.confirmPassword = "Passwords do not match";
    }

    if (user.age < 18) {
      errors.age = "You must be at least 18 years old";
    }

    const validRoles = ["voter", "admin", "moderator"];
    if (!validRoles.includes(user.role)) {
      errors.role = "Invalid role selected";
    }

    return errors;
  }


  const navigate = useNavigate()

  const handleChange = (e) => {
    const { id, value } = e.target;
    setUser(prev => ({ ...prev, [id]: value }))

  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    setError("")



    if (user.password !== user.confirmPassword) {
      setError("Passwords don't match")
      return
    }

    const errs = validateForm(user);
    if (Object.keys(errs).length > 0) {
      setError(Object.values(errs).join(", "))
      return
    }


    setIsLoading(true)

    setUser(prev => {
      return { ...prev, isEligible: prev.age >= 18 }
    })

    console.log(user);


    registerVoter(user).then((res) => {
      if (res) {
        navigate("/login")

      } else {
        setError("Failed to register. Please try again later")
      }
    }).finally(() => {
      setIsLoading(false);
    })


    // setTimeout(() => {
    // setIsLoading(false)
    //   navigate("/login")
    // }, 1000)
  }

  return (
    <div className="container flex min-h-[calc(100vh-4rem)] items-center justify-center py-8">
      <Card className="w-full max-w-md mx-auto animate-in fade-in-50 slide-in-from-bottom-8 duration-700">
        <CardHeader className="space-y-1">
          <CardTitle className="text-2xl font-bold">Create an account</CardTitle>
          <CardDescription>Enter your information to create an account</CardDescription>
        </CardHeader>
        <form onSubmit={handleSubmit}>
          <CardContent className="space-y-4">
            {error && <div className="p-3 text-sm bg-destructive/10 text-destructive rounded-md">{error}</div>}
            <div className="space-y-2">
              <Label htmlFor="name">Full Name</Label>
              <Input id="name" placeholder="Siva Kumar" value={user.name} onChange={handleChange} required />
            </div>
            <div className="space-y-2">
              <Label htmlFor="email">Email</Label>
              <Input
                id="email"
                type="email"
                placeholder="m@example.com"
                value={user.email}
                onChange={handleChange}
                required
              />
            </div>
            <div className="space-y-2">
              <Label htmlFor="password">Password</Label>
              <Input
                id="password"
                type="password"
                value={user.password}
                onChange={handleChange}
                required
              />
            </div>
            <div className="space-y-2">
              <Label htmlFor="confirmPassword">Confirm Password</Label>
              <Input
                id="confirmPassword"
                type="password"
                value={user.confirmPassword}
                onChange={handleChange}
                required
              />
            </div>
            <div className="space-y-2">
              <Label htmlFor="age">Age</Label>
              <Input
                id="age"
                type="number"
                value={user.age}
                onChange={handleChange}
                required
              />
            </div>
            <div className="space-y-2">
              <Label>Account Type</Label>
              <div className="flex space-x-4">
                <div className="flex items-center space-x-2">
                  <input
                    type="radio"
                    id="voter"
                    name="role"
                    value="voter"
                    checked={user.role === "voter"}
                    onChange={handleChange}
                    className="h-4 w-4 border-gray-300 text-primary focus:ring-primary"
                  />
                  <Label htmlFor="voter">Voter</Label>
                </div>
                <div className="flex items-center space-x-2">
                  <input
                    type="radio"
                    id="candidate"
                    name="role"
                    value="candidate"
                    checked={user.role === "candidate"}
                    onChange={handleChange}
                    className="h-4 w-4 border-gray-300 text-primary focus:ring-primary"
                  />
                  <Label htmlFor="candidate">Candidate</Label>
                </div>
              </div>
            </div>
            <div className="flex items-center space-x-2">
              <input
                type="checkbox"
                id="terms"
                required
                className="h-4 w-4 rounded border-gray-300 text-primary focus:ring-primary"
              />
              <Label
                htmlFor="terms"
                className="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
              >
                I agree to the{" "}
                <Link to="/terms" className="text-primary hover:underline">
                  terms and conditions
                </Link>
              </Label>
            </div>
          </CardContent>
          <CardFooter className="flex flex-col space-y-4">
            <Button type="submit" className="w-full" disabled={isLoading}>
              {isLoading ? (
                <div className="flex items-center justify-center">
                  <svg
                    className="animate-spin -ml-1 mr-3 h-4 w-4 text-white"
                    xmlns="http://www.w3.org/2000/svg"
                    fill="none"
                    viewBox="0 0 24 24"
                  >
                    <circle
                      className="opacity-25"
                      cx="12"
                      cy="12"
                      r="10"
                      stroke="currentColor"
                      strokeWidth="4"
                    ></circle>
                    <path
                      className="opacity-75"
                      fill="currentColor"
                      d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"
                    ></path>
                  </svg>
                  Creating account...
                </div>
              ) : (
                "Create account"
              )}
            </Button>
            <div className="text-center text-sm">
              Already have an account?{" "}
              <Link to="/login" className="text-primary hover:underline">
                Login
              </Link>
            </div>
          </CardFooter>
        </form>
      </Card>
    </div>
  )
}

