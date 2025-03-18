

const ApiUrl = import.meta.env.VITE_API_VOTESYSTEM_URL;

const registerVoter = async (user) => {
  return fetch(`${ApiUrl}/registervoter`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(user),
  }).then((response) => response.json())
    .then(data => {
      console.log(data);
      if (data.status) {
        return data.voterId
      }
      return null;
    }).catch(err => {
      console.log(err);
      return false;
    })
}


const signInVoter = async (user) => {
  return fetch(`${ApiUrl}/signvoter?email=${encodeURIComponent(user.email)}&password=${encodeURIComponent(user.password)}`, {
    method: "GET",
  }).then((response) => response.json())
    .then(data => {
      console.log(data);

      if (data.status) {
        return data
      }

      return null;
    }).catch(err => {
      console.log(err);
      return false;
    })

}


const getElections = async (eleType) => {
  return fetch(`${ApiUrl}/getelections?electionType=${eleType}`, {
    method: "GET",
  }).then((response) => response.json())
    .then(data => {
      console.log(data);

      if (data.status) {
        return data.elections
      }
      return null;
    }).catch(err => {
      console.log(err);
      return false;
    })
}


const getCandidates = async (eleId) => {
  console.log(eleId);

  return fetch(`${ApiUrl}/getcandidates?eleId=${eleId}`, {
    method: "GET",
  }).then((response) => response.json())
    .then(data => {
      console.log(data);

      if (data.status) {
        return data.candidates
      }
      return null;
    }).catch(err => {
      console.log(err);
      return false;
    })
}

export { registerVoter, signInVoter, getElections, getCandidates }