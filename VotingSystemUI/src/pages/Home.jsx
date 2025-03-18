import { Link } from "react-router-dom"
import { Button } from "../components/Button"

export default function Home() {
  return (
    <main className="flex min-h-screen flex-col">
      <section className="w-full py-12 md:py-24 lg:py-32 xl:py-48 bg-gradient-to-b from-background to-muted">
        <div className="container px-4 md:px-6">
          <div className="grid gap-6 lg:grid-cols-[1fr_400px] lg:gap-12 xl:grid-cols-[1fr_600px]">
            <div className="flex flex-col justify-center space-y-4">
              <div className="space-y-2">
                <h1 className="text-3xl font-bold tracking-tighter sm:text-5xl xl:text-6xl/none">
                  zVoting System
                </h1>
                <p className="max-w-[600px] text-muted-foreground md:text-xl">
                  A secure and transparent platform for conducting elections. Vote with confidence, track results in
                  real-time.
                </p>
              </div>
              <div className="flex flex-col gap-2 min-[400px]:flex-row">
                <Link to="/login">
                  <Button className="w-full min-[400px]:w-auto">Login</Button>
                </Link>
                <Link to="/signup">
                  <Button variant="outline" className="w-full min-[400px]:w-auto">
                    Sign Up
                  </Button>
                </Link>
              </div>
            </div>
            <img
              src="https://pixelplex.io/wp-content/uploads/2021/02/blockchain-voting-main-1600.jpg"
              width={550}
              height={310}
              alt="Hero"
              className="mx-auto aspect-video overflow-hidden rounded-xl object-cover object-center sm:w-full lg:order-last"
            />
          </div>
        </div>
      </section>
      <section className="w-full py-12 md:py-24 lg:py-32 bg-muted dark:bg-muted/50">
        <div className="container px-4 md:px-6">
          <div className="flex flex-col items-center justify-center space-y-4 text-center">
            <div className="space-y-2">
              <h2 className="text-3xl font-bold tracking-tighter sm:text-4xl md:text-5xl">Features</h2>
              <p className="max-w-[900px] text-muted-foreground md:text-xl/relaxed lg:text-base/relaxed xl:text-xl/relaxed">
                Our platform offers a comprehensive set of features for both voters and administrators.
              </p>
            </div>
            <div className="grid w-full grid-cols-1 gap-6 md:grid-cols-3 lg:gap-12">
              <div className="flex flex-col items-center space-y-2 rounded-lg border p-6 shadow-sm transition-all hover:shadow-md">
                <div className="rounded-full bg-primary/10 p-4">
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
                    className="h-6 w-6 text-primary"
                  >
                    <path d="M2 20h.01"></path>
                    <path d="M7 20v-4"></path>
                    <path d="M12 20v-8"></path>
                    <path d="M17 20V8"></path>
                    <path d="M22 4v16"></path>
                  </svg>
                </div>
                <h3 className="text-xl font-bold">Real-time Results</h3>
                <p className="text-muted-foreground">
                  Track election results as they happen with our real-time dashboard.
                </p>
              </div>
              <div className="flex flex-col items-center space-y-2 rounded-lg border p-6 shadow-sm transition-all hover:shadow-md">
                <div className="rounded-full bg-primary/10 p-4">
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
                    className="h-6 w-6 text-primary"
                  >
                    <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10"></path>
                  </svg>
                </div>
                <h3 className="text-xl font-bold">Secure Voting</h3>
                <p className="text-muted-foreground">Our platform ensures secure and tamper-proof voting processes.</p>
              </div>
              <div className="flex flex-col items-center space-y-2 rounded-lg border p-6 shadow-sm transition-all hover:shadow-md">
                <div className="rounded-full bg-primary/10 p-4">
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
                    className="h-6 w-6 text-primary"
                  >
                    <path d="M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2"></path>
                    <circle cx="9" cy="7" r="4"></circle>
                    <path d="M22 21v-2a4 4 0 0 0-3-3.87"></path>
                    <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
                  </svg>
                </div>
                <h3 className="text-xl font-bold">User Roles</h3>
                <p className="text-muted-foreground">
                  Different access levels for voters, candidates, and administrators.
                </p>
              </div>
            </div>
          </div>
        </div>
      </section>
      <section className="w-full py-12 md:py-24 lg:py-32">
        <div className="container px-4 md:px-6">
          <div className="flex flex-col items-center justify-center space-y-4 text-center">
            <div className="space-y-2">
              <h2 className="text-3xl font-bold tracking-tighter sm:text-4xl md:text-5xl">How It Works</h2>
              <p className="max-w-[900px] text-muted-foreground md:text-xl/relaxed lg:text-base/relaxed xl:text-xl/relaxed">
                Our voting system is designed to be simple, secure, and transparent.
              </p>
            </div>
            <div className="grid w-full grid-cols-1 gap-8 md:grid-cols-3">
              <div className="flex flex-col items-center space-y-2">
                <div className="flex h-12 w-12 items-center justify-center rounded-full bg-primary text-xl font-bold text-primary-foreground">
                  1
                </div>
                <h3 className="text-xl font-bold">Register</h3>
                <p className="text-muted-foreground">
                  Create an account and verify your identity to participate in elections.
                </p>
              </div>
              <div className="flex flex-col items-center space-y-2">
                <div className="flex h-12 w-12 items-center justify-center rounded-full bg-primary text-xl font-bold text-primary-foreground">
                  2
                </div>
                <h3 className="text-xl font-bold">Vote</h3>
                <p className="text-muted-foreground">Cast your vote securely in active elections from anywhere.</p>
              </div>
              <div className="flex flex-col items-center space-y-2">
                <div className="flex h-12 w-12 items-center justify-center rounded-full bg-primary text-xl font-bold text-primary-foreground">
                  3
                </div>
                <h3 className="text-xl font-bold">Track</h3>
                <p className="text-muted-foreground">Monitor election results in real-time as votes are counted.</p>
              </div>
            </div>
            <div className="mt-8">
              <Link to="/signup">
                <Button size="lg">Get Started</Button>
              </Link>
            </div>
          </div>
        </div>
      </section>
      {/* <footer className="w-full border-t py-6 md:py-8">
        <div className="container flex flex-col items-center justify-center gap-4 md:flex-row md:gap-8">
          <p className="text-center text-sm text-muted-foreground md:text-left">
            © 2023 VoteSystem. All rights reserved.
          </p>
          <div className="flex gap-4">
            <Link to="/terms" className="text-sm text-muted-foreground hover:underline">
              Terms
            </Link>
            <Link to="/privacy" className="text-sm text-muted-foreground hover:underline">
              Privacy
            </Link>
            <Link to="/contact" className="text-sm text-muted-foreground hover:underline">
              Contact
            </Link>
          </div>
        </div>
      </footer> */}
    </main>
  )
}

