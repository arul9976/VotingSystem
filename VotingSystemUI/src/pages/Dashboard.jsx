"use client"

import { useEffect, useRef, useState } from "react"
import { useNavigate } from "react-router-dom"
import { useDispatch, useSelector } from "react-redux"
import { Button } from "../components/Button"
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "../components/Card"
import { ElectionDetailsModal } from "../components/ElectionDetailsModal"
import { getElections } from "../Fetch/voterFetch"
import { setElections } from "../lib/features/elections/electionSlice"

export default function Dashboard() {
  const navigate = useNavigate()
  const isAuthenticated = useSelector((state) => state.auth.isAuthenticated)
  const userRole = useSelector((state) => state.auth.userRole)
  const elections = useSelector((state) => state.electionState.elections)
  const candidates = useSelector((state) => state.electionState.candidates)
  const [activeTab, setActiveTab] = useState("ONGOING");
  const [activeTabModel, setActiveTabModel] = useState("overview");

  const selectedElection = useRef(null)
  const [isModalOpen, setIsModalOpen] = useState(false)
  const dispatch = useDispatch();

  // useEffect(() => {
  //   if (!isAuthenticated) {
  //     navigate("/login")
  //   }
  // }, [isAuthenticated, navigate])

  // if (!isAuthenticated) {
  //   return null
  // }

  const openElectionDetails = (election) => {
    selectedElection.current = election;
    setIsModalOpen(true)
  }

  const closeElectionDetails = () => {
    setIsModalOpen(false)
  }

  useEffect(() => {
    setActiveTabModel("overview")
  }, [isModalOpen])

  useEffect(() => {
    // console.log(userRole);
    // navigate("/login");

    if (isAuthenticated)
      getElections(activeTab)
        .then(ele => {

          if (ele && ele.length > 0)
            dispatch(setElections(ele))

        }).catch(err => {
          console.log(err);

        })

  }, [isAuthenticated, activeTab])

  useEffect(() => {
    if (Object.keys(elections).length > 0)
      console.log(elections);

  }, [elections])

  return (
    <div className="container py-8 animate-in fade-in-50 duration-500">
      <h1 className="text-3xl font-bold mb-6">Dashboard</h1>

      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-4 mb-8">
        <Card className="transition-all hover:shadow-md">
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Total Elections</CardTitle>
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
              <rect x="3" y="4" width="18" height="18" rx="2" ry="2"></rect>
              <line x1="16" y1="2" x2="16" y2="6"></line>
              <line x1="8" y1="2" x2="8" y2="6"></line>
              <line x1="3" y1="10" x2="21" y2="10"></line>
            </svg>
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">12</div>
            <p className="text-xs text-muted-foreground">+2 from last month</p>
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
              <circle cx="12" cy="12" r="10"></circle>
              <polyline points="12 6 12 12 16 14"></polyline>
            </svg>
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">3</div>
            <p className="text-xs text-muted-foreground">+1 from last month</p>
          </CardContent>
        </Card>
        <Card className="transition-all hover:shadow-md">
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Your Votes</CardTitle>
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
              <path d="M9 12l2 2 4-4"></path>
              <path d="M5 7c0-1.1.9-2 2-2h10a2 2 0 0 1 2 2v12H5V7z"></path>
              <path d="M22 19H2"></path>
            </svg>
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">7</div>
            <p className="text-xs text-muted-foreground">+2 from last month</p>
          </CardContent>
        </Card>
        <Card className="transition-all hover:shadow-md">
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Completed Elections</CardTitle>
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
              <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"></path>
              <polyline points="22 4 12 14.01 9 11.01"></polyline>
            </svg>
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">9</div>
            <p className="text-xs text-muted-foreground">+3 from last month</p>
          </CardContent>
        </Card>
      </div>

      <div className="space-y-4">
        <div className="inline-flex h-10 items-center justify-center rounded-md bg-muted p-1 text-muted-foreground">
          <button
            className={`${activeTab === "ONGOING" && 'bg-background/50'} inline-flex items-center justify-center whitespace-nowrap rounded-sm px-3 py-1.5 text-sm font-medium ring-offset-background transition-all focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 ${activeTab === "ongoing"
              ? "bg-background text-foreground shadow-sm"
              : "hover:bg-background/50 hover:text-foreground"
              }`}
            onClick={() => setActiveTab("ONGOING")}
          >
            Ongoing Elections
          </button>
          <button
            className={`${activeTab === "UPCOMING" && 'bg-background/50'} inline-flex items-center justify-center whitespace-nowrap rounded-sm px-3 py-1.5 text-sm font-medium ring-offset-background transition-all focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 ${activeTab === "upcoming"
              ? "bg-background text-foreground shadow-sm"
              : "hover:bg-background/50 hover:text-foreground"
              }`}
            onClick={() => setActiveTab("UPCOMING")}
          >
            Upcoming Elections
          </button>
          <button
            className={`${activeTab === "ENDED" && 'bg-background/50'} inline-flex items-center justify-center whitespace-nowrap rounded-sm px-3 py-1.5 text-sm font-medium ring-offset-background transition-all focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 ${activeTab === "completed"
              ? "bg-background text-foreground shadow-sm"
              : "hover:bg-background/50 hover:text-foreground"
              }`}
            onClick={() => setActiveTab("ENDED")}
          >
            Completed Elections
          </button>
          {userRole === "admin" && (
            <button
              className={`${activeTab === "ONGOING" && 'bg-background/50'} inline-flex items-center justify-center whitespace-nowrap rounded-sm px-3 py-1.5 text-sm font-medium ring-offset-background transition-all focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 ${activeTab === "admin"
                ? "bg-background text-foreground shadow-sm"
                : "hover:bg-background/50 hover:text-foreground"
                }`}
              onClick={() => setActiveTab("admin")}
            >
              Admin Panel
            </button>
          )}
        </div>

        {(activeTab === "ONGOING" && (elections[activeTab] && elections[activeTab].length > 0)) && (
          <div className="space-y-4">
            <h2 className="text-xl font-semibold mb-4">Ongoing Elections</h2>
            <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
              {elections[activeTab].map((election, i) => {
                return (
                  <Card key={i} className="transition-all hover:shadow-md">
                    <CardHeader>
                      <CardTitle>{election.electionName}</CardTitle>
                      <CardDescription>Ends in {i * 2} days</CardDescription>
                    </CardHeader>
                    <CardContent>
                      <div className="space-y-2">
                        <div className="flex justify-between">
                          <span>Candidates:</span>
                          <span>{election.totalCandidates}</span>
                        </div>
                        <div className="flex justify-between">
                          <span>Total Votes:</span>
                          <span>{election.totalVotes}</span>
                        </div>
                        <div className="flex justify-between">
                          <span>Your Status:</span>
                          <span className="text-green-500 font-medium">Voted</span>
                        </div>
                        <Button className="w-full mt-4" onClick={() => openElectionDetails(election)}>
                          View Details
                        </Button>
                      </div>
                    </CardContent>
                  </Card>
                )
              })}
            </div>
          </div>
        )}

        {activeTab === "UPCOMING" && (
          <div className="space-y-4">
            <h2 className="text-xl font-semibold mb-4">Upcoming Elections</h2>
            <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
              {[1, 2, 3, 4].map((i) => {
                const election = {
                  id: i + 3,
                  title: `State Election ${i}`,
                  description: `Upcoming state election for various positions in district ${i}.`,
                  startDate: new Date(Date.now() + i * 86400000 * 3).toISOString(),
                  candidates: 3 + i,
                  status: "upcoming",
                }

                return (
                  <Card key={i} className="transition-all hover:shadow-md">
                    <CardHeader>
                      <CardTitle>{election.title}</CardTitle>
                      <CardDescription>Starts in {i * 3} days</CardDescription>
                    </CardHeader>
                    <CardContent>
                      <div className="space-y-2">
                        <div className="flex justify-between">
                          <span>Candidates:</span>
                          <span>{election.candidates}</span>
                        </div>
                        <div className="flex justify-between">
                          <span>Registration:</span>
                          <span className="text-amber-500 font-medium">Open</span>
                        </div>
                        <Button className="w-full mt-4" onClick={() => openElectionDetails(election)}>
                          Register to Vote
                        </Button>
                      </div>
                    </CardContent>
                  </Card>
                )
              })}
            </div>
          </div>
        )}

        {(activeTab === "ENDED" && (elections[activeTab] && elections[activeTab].length > 0)) && (

          <div className="space-y-4">
            <h2 className="text-xl font-semibold mb-4">Completed Elections</h2>
            <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
              {elections[activeTab].map((election, i) => {

                return (
                  <Card key={i} className="transition-all hover:shadow-md">
                    <CardHeader>
                      <CardTitle>{election.electionName}</CardTitle>
                      <CardDescription>
                        Completed on {new Date(election.endDate).toLocaleDateString()}
                      </CardDescription>
                    </CardHeader>
                    <CardContent>
                      <div className="space-y-2">
                        <div className="flex justify-between">
                          <span>Winner:</span>
                          <span className="font-medium">{election.winner || "----"}</span>
                        </div>
                        <div className="flex justify-between">
                          <span>Total Votes:</span>
                          <span>{election.totalVotes}</span>
                        </div>
                        <div className="flex justify-between">
                          <span>Your Status:</span>
                          <span className="text-blue-500 font-medium">Participated</span>
                        </div>
                        <Button className="w-full mt-4" onClick={() => openElectionDetails(election)}>
                          View Results
                        </Button>
                      </div>
                    </CardContent>
                  </Card>
                )
              })}
            </div>
          </div>
        )}

        {activeTab === "admin" && userRole === "admin" && (
          <div className="space-y-4">
            <h2 className="text-xl font-semibold mb-4">Admin Quick Actions</h2>
            <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
              <Card className="transition-all hover:shadow-md">
                <CardHeader>
                  <CardTitle>Create Election</CardTitle>
                  <CardDescription>Set up a new election</CardDescription>
                </CardHeader>
                <CardContent>
                  <Button className="w-full" onClick={() => navigate("/admin")}>
                    Create New
                  </Button>
                </CardContent>
              </Card>
              <Card className="transition-all hover:shadow-md">
                <CardHeader>
                  <CardTitle>Pending Approvals</CardTitle>
                  <CardDescription>Candidate applications</CardDescription>
                </CardHeader>
                <CardContent>
                  <div className="text-2xl font-bold mb-2">7</div>
                  <Button className="w-full" onClick={() => navigate("/admin")}>
                    Review
                  </Button>
                </CardContent>
              </Card>
              <Card className="transition-all hover:shadow-md">
                <CardHeader>
                  <CardTitle>Election Analytics</CardTitle>
                  <CardDescription>View detailed statistics</CardDescription>
                </CardHeader>
                <CardContent>
                  <Button className="w-full" onClick={() => navigate("/admin")}>
                    View Analytics
                  </Button>
                </CardContent>
              </Card>
            </div>
          </div>
        )}
      </div>

      <ElectionDetailsModal activeTab={activeTabModel} setActiveTab={setActiveTabModel} isOpen={isModalOpen} dispatch={dispatch} candidates={candidates} onClose={closeElectionDetails} election={selectedElection.current} />
    </div>
  )
}

