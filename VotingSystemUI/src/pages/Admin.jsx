"use client"

import { useEffect, useState } from "react"
import { useNavigate } from "react-router-dom"
import { useSelector } from "react-redux"
import { Button } from "../components/Button"
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "../components/Card"
import { Input } from "../components/Input"
import { Label } from "../components/Label"
import { ElectionDetailsModal } from "../components/ElectionDetailsModal"

export default function Admin() {
  const navigate = useNavigate()
  const isAuthenticated = useSelector((state) => state.auth.isAuthenticated)
  const userRole = useSelector((state) => state.auth.userRole)
  const [activeTab, setActiveTab] = useState("elections")
  const [isCreateModalOpen, setIsCreateModalOpen] = useState(false)
  const [selectedElection, setSelectedElection] = useState(null)
  const [isElectionModalOpen, setIsElectionModalOpen] = useState(false)

  const [electionDetail, setElectionDetail] = useState({});

  const handleCreateElectionModelChange = (e) => {

    setElectionDetail({ ...electionDetail, [e.target.name]: e.target.value });
  }

  const handleCreateElection = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch("/api/elections", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(electionDetail),
      })

      if (!response.ok) {
        alert("Failed to create election")
      }

      const createdElection = await response.json()
      console.log("Created Election: ", createdElection);
      alert("Election created successfully")
      setIsCreateModalOpen(false)

    } catch (error) {
      console.error("Error creating election:", error.message)
    }
  }

  useEffect(() => {
    if (!isAuthenticated || userRole !== "admin") {
      navigate("/login")
    }
  }, [isAuthenticated, userRole, navigate])

  if (!isAuthenticated || userRole !== "admin") {
    return null
  }

  const openElectionDetails = (election) => {
    setSelectedElection(election)
    setIsElectionModalOpen(true)
  }

  const closeElectionDetails = () => {
    setIsElectionModalOpen(false)
  }

  return (
    <div className="container py-8 animate-in fade-in-50 duration-500">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-3xl font-bold">Admin Dashboard</h1>
        <Button onClick={() => setIsCreateModalOpen(true)}>
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
            className="mr-2 h-4 w-4"
          >
            <line x1="12" y1="5" x2="12" y2="19"></line>
            <line x1="5" y1="12" x2="19" y2="12"></line>
          </svg>
          Create New Election
        </Button>
      </div>

      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-4 mb-8">
        <Card className="transition-all hover:shadow-md">
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Total Users</CardTitle>
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
              className="h-4 w-4 text-muted-foreground"
            >
              <path d="M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2"></path>
              <circle cx="9" cy="7" r="4"></circle>
              <path d="M22 21v-2a4 4 0 0 0-3-3.87"></path>
              <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
            </svg>
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">2,853</div>
            <p className="text-xs text-muted-foreground">+180 from last month</p>
          </CardContent>
        </Card>
        <Card className="transition-all hover:shadow-md">
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Active Elections</CardTitle>
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
              className="h-4 w-4 text-muted-foreground"
            >
              <path d="M12 20h9"></path>
              <path d="M16.5 3.5a2.121 2.121 0 0 1 3 3L7 19l-4 1 1-4L16.5 3.5z"></path>
            </svg>
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">3</div>
            <p className="text-xs text-muted-foreground">+1 from last month</p>
          </CardContent>
        </Card>
        <Card className="transition-all hover:shadow-md">
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Pending Approvals</CardTitle>
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
              className="h-4 w-4 text-muted-foreground"
            >
              <path d="M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2"></path>
              <circle cx="9" cy="7" r="4"></circle>
              <path d="M22 21v-2a4 4 0 0 0-3-3.87"></path>
              <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
            </svg>
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">7</div>
            <p className="text-xs text-muted-foreground">+3 from last week</p>
          </CardContent>
        </Card>
        <Card className="transition-all hover:shadow-md">
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Total Votes</CardTitle>
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
              className="h-4 w-4 text-muted-foreground"
            >
              <rect x="2" y="2" width="20" height="8" rx="2" ry="2"></rect>
              <rect x="2" y="14" width="20" height="8" rx="2" ry="2"></rect>
              <line x1="6" y1="6" x2="6.01" y2="6"></line>
              <line x1="6" y1="18" x2="6.01" y2="18"></line>
            </svg>
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">12,234</div>
            <p className="text-xs text-muted-foreground">+2,345 from last month</p>
          </CardContent>
        </Card>
      </div>

      <div className="space-y-4">
        <div className="inline-flex h-10 items-center justify-center rounded-md bg-muted p-1 text-muted-foreground">
          <button
            className={`inline-flex items-center justify-center whitespace-nowrap rounded-sm px-3 py-1.5 text-sm font-medium ring-offset-background transition-all focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 ${activeTab === "elections"
              ? "bg-background text-foreground shadow-sm"
              : "hover:bg-background/50 hover:text-foreground"
              }`}
            onClick={() => setActiveTab("elections")}
          >
            Manage Elections
          </button>
          <button
            className={`inline-flex items-center justify-center whitespace-nowrap rounded-sm px-3 py-1.5 text-sm font-medium ring-offset-background transition-all focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 ${activeTab === "candidates"
              ? "bg-background text-foreground shadow-sm"
              : "hover:bg-background/50 hover:text-foreground"
              }`}
            onClick={() => setActiveTab("candidates")}
          >
            Approve Candidates
          </button>
          <button
            className={`inline-flex items-center justify-center whitespace-nowrap rounded-sm px-3 py-1.5 text-sm font-medium ring-offset-background transition-all focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 ${activeTab === "parties"
              ? "bg-background text-foreground shadow-sm"
              : "hover:bg-background/50 hover:text-foreground"
              }`}
            onClick={() => setActiveTab("parties")}
          >
            Political Parties
          </button>
          <button
            className={`inline-flex items-center justify-center whitespace-nowrap rounded-sm px-3 py-1.5 text-sm font-medium ring-offset-background transition-all focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 ${activeTab === "users"
              ? "bg-background text-foreground shadow-sm"
              : "hover:bg-background/50 hover:text-foreground"
              }`}
            onClick={() => setActiveTab("users")}
          >
            User Management
          </button>
        </div>

        {activeTab === "elections" && (
          <Card>
            <CardHeader>
              <CardTitle>All Elections</CardTitle>
              <CardDescription>Manage all elections in the system</CardDescription>
            </CardHeader>
            <CardContent>
              <div className="rounded-md border">
                <div className="grid grid-cols-5 bg-muted p-4 font-medium">
                  <div>Name</div>
                  <div>Start Date</div>
                  <div>End Date</div>
                  <div>Status</div>
                  <div>Actions</div>
                </div>
                {[1, 2, 3, 4, 5].map((i) => {
                  const election = {
                    id: i,
                    title: `Election ${i}`,
                    description: `Description for election ${i}`,
                    startDate: new Date(2023, i, i * 2).toISOString(),
                    endDate: new Date(2023, i, i * 2 + 14).toISOString(),
                    status: i === 1 || i === 2 ? "ongoing" : i === 3 ? "upcoming" : "completed",
                    candidates: 5,
                    votes: i === 1 || i === 2 ? 120 + i * 45 : i === 3 ? 0 : 1000 + i * 230,
                  }

                  return (
                    <div key={i} className="grid grid-cols-5 p-4 border-t items-center">
                      <div className="font-medium">{election.title}</div>
                      <div>{new Date(election.startDate).toLocaleDateString()}</div>
                      <div>{new Date(election.endDate).toLocaleDateString()}</div>
                      <div>
                        <span
                          className={`px-2 py-1 rounded-full text-xs font-medium ${election.status === "ongoing"
                            ? "bg-green-100 text-green-800 dark:bg-green-800/20 dark:text-green-400"
                            : election.status === "upcoming"
                              ? "bg-amber-100 text-amber-800 dark:bg-amber-800/20 dark:text-amber-400"
                              : "bg-blue-100 text-blue-800 dark:bg-blue-800/20 dark:text-blue-400"
                            }`}
                        >
                          {election.status === "ongoing"
                            ? "Active"
                            : election.status === "upcoming"
                              ? "Upcoming"
                              : "Completed"}
                        </span>
                      </div>
                      <div className="flex space-x-2">
                        <Button variant="outline" size="sm">
                          Edit
                        </Button>
                        <Button variant="outline" size="sm" onClick={() => openElectionDetails(election)}>
                          View
                        </Button>
                      </div>
                    </div>
                  )
                })}
              </div>
            </CardContent>
          </Card>
        )}

        {activeTab === "candidates" && (
          <Card>
            <CardHeader>
              <CardTitle>Candidate Approvals</CardTitle>
              <CardDescription>Review and approve candidate applications</CardDescription>
            </CardHeader>
            <CardContent>
              <div className="rounded-md border">
                <div className="grid grid-cols-5 bg-muted p-4 font-medium">
                  <div>Name</div>
                  <div>Party</div>
                  <div>Election</div>
                  <div>Status</div>
                  <div>Actions</div>
                </div>
                {[1, 2, 3, 4, 5, 6, 7].map((i) => (
                  <div key={i} className="grid grid-cols-5 p-4 border-t items-center">
                    <div className="font-medium">Candidate {i}</div>
                    <div>Party {(i % 3) + 1}</div>
                    <div>Election {(i % 5) + 1}</div>
                    <div>
                      <span className="px-2 py-1 rounded-full text-xs font-medium bg-amber-100 text-amber-800 dark:bg-amber-800/20 dark:text-amber-400">
                        Pending
                      </span>
                    </div>
                    <div className="flex space-x-2">
                      <Button
                        variant="outline"
                        size="sm"
                        className="bg-green-500/10 hover:bg-green-500/20 text-green-600 dark:text-green-400 border-green-500/20"
                      >
                        Approve
                      </Button>
                      <Button
                        variant="outline"
                        size="sm"
                        className="bg-red-500/10 hover:bg-red-500/20 text-red-600 dark:text-red-400 border-red-500/20"
                      >
                        Reject
                      </Button>
                    </div>
                  </div>
                ))}
              </div>
            </CardContent>
          </Card>
        )}

        {activeTab === "parties" && (
          <Card>
            <CardHeader>
              <CardTitle>Political Parties</CardTitle>
              <CardDescription>Manage political parties in the system</CardDescription>
            </CardHeader>
            <CardContent>
              <div className="flex justify-end mb-4">
                <Button>
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
                    className="mr-2 h-4 w-4"
                  >
                    <line x1="12" y1="5" x2="12" y2="19"></line>
                    <line x1="5" y1="12" x2="19" y2="12"></line>
                  </svg>
                  Add Party
                </Button>
              </div>
              <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
                {[1, 2, 3, 4, 5].map((i) => (
                  <Card key={i} className="transition-all hover:shadow-md">
                    <CardHeader>
                      <CardTitle>Party {i}</CardTitle>
                      <CardDescription>Founded: {2000 + i * 3}</CardDescription>
                    </CardHeader>
                    <CardContent>
                      <div className="space-y-2">
                        <div className="flex justify-between">
                          <span>Members:</span>
                          <span>{50 + i * 12}</span>
                        </div>
                        <div className="flex justify-between">
                          <span>Candidates:</span>
                          <span>{5 + i}</span>
                        </div>
                        <div className="flex space-x-2 mt-4">
                          <Button variant="outline" className="flex-1">
                            Edit
                          </Button>
                          <Button variant="outline" className="flex-1">
                            View
                          </Button>
                        </div>
                      </div>
                    </CardContent>
                  </Card>
                ))}
              </div>
            </CardContent>
          </Card>
        )}

        {activeTab === "users" && (
          <Card>
            <CardHeader>
              <CardTitle>User Management</CardTitle>
              <CardDescription>Manage users in the system</CardDescription>
            </CardHeader>
            <CardContent>
              <div className="rounded-md border">
                <div className="grid grid-cols-5 bg-muted p-4 font-medium">
                  <div>Name</div>
                  <div>Email</div>
                  <div>Role</div>
                  <div>Status</div>
                  <div>Actions</div>
                </div>
                {[1, 2, 3, 4, 5, 6, 7, 8].map((i) => (
                  <div key={i} className="grid grid-cols-5 p-4 border-t items-center">
                    <div className="font-medium">User {i}</div>
                    <div>user{i}@example.com</div>
                    <div>{i === 1 ? "Admin" : i % 3 === 0 ? "Candidate" : "Voter"}</div>
                    <div>
                      <span
                        className={`px-2 py-1 rounded-full text-xs font-medium ${i % 4 === 0
                          ? "bg-amber-100 text-amber-800 dark:bg-amber-800/20 dark:text-amber-400"
                          : "bg-green-100 text-green-800 dark:bg-green-800/20 dark:text-green-400"
                          }`}
                      >
                        {i % 4 === 0 ? "Pending" : "Active"}
                      </span>
                    </div>
                    <div className="flex space-x-2">
                      <Button variant="outline" size="sm">
                        Edit
                      </Button>
                      <Button
                        variant="outline"
                        size="sm"
                        className="bg-red-500/10 hover:bg-red-500/20 text-red-600 dark:text-red-400 border-red-500/20"
                      >
                        Disable
                      </Button>
                    </div>
                  </div>
                ))}
              </div>
            </CardContent>
          </Card>
        )}
      </div>

      {isCreateModalOpen && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-background/80 backdrop-blur-sm animate-in fade-in-0">
          <div className="fixed inset-0 z-50 flex items-center justify-center">
            <Card className="w-full max-w-lg max-h-[90vh] overflow-auto animate-in zoom-in-95 duration-300">
              <CardHeader className="flex flex-row items-center justify-between">
                <CardTitle>Create New Election</CardTitle>
                <Button variant="ghost" size="icon" onClick={() => setIsCreateModalOpen(false)} className="h-8 w-8">
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
                    className="h-4 w-4"
                  >
                    <line x1="18" y1="6" x2="6" y2="18"></line>
                    <line x1="6" y1="6" x2="18" y2="18"></line>
                  </svg>
                  <span className="sr-only">Close</span>
                </Button>
              </CardHeader>
              <CardContent>
                <form onSubmit={handleCreateElection} className="space-y-4">
                  <div className="space-y-2">
                    <Label htmlFor="title">Election Title</Label>
                    <Input onChage={handleCreateElectionModelChange} name="electionName" id="title" placeholder="Enter election title" />
                  </div>
                  {/* <div className="space-y-2">
                    <Label htmlFor="description">Description</Label>
                    <textarea
                      id="description"
                      onChage={handleCreateElectionModelChange}
                      placeholder="Enter election description"
                      className="flex min-h-[80px] w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50"
                    />
                  </div> */}
                  <div className="grid grid-cols-2 gap-4">
                    <div className="space-y-2">
                      <Label htmlFor="startDate">Start Date</Label>
                      <Input onChage={handleCreateElectionModelChange} name="startDate" id="startDate" type="date" />
                    </div>
                    <div className="space-y-2">
                      <Label htmlFor="endDate">End Date</Label>
                      <Input onChage={handleCreateElectionModelChange} name="endDate" id="endDate" type="date" />
                    </div>
                  </div>
                  {/* <div className="space-y-2">
                    <Label htmlFor="eligibility">Eligibility Requirements</Label>
                    <Input onChage={handleCreateElectionModelChange} id="eligibility" placeholder="Enter eligibility requirements" />
                  </div> */}
                  <div className="flex justify-end space-x-2 pt-4">
                    <Button variant="outline" onClick={() => setIsCreateModalOpen(false)}>
                      Cancel
                    </Button>
                    <Button type="submit">Create Election</Button>
                  </div>
                </form>
              </CardContent>
            </Card>
          </div>
        </div>
      )}

      <ElectionDetailsModal isOpen={isElectionModalOpen} onClose={closeElectionDetails} election={selectedElection} />
    </div>
  )
}

