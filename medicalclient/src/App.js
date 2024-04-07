import { useState } from 'react';
import { Route, BrowserRouter as Router, Routes } from 'react-router-dom';
import './App.css';
import Home from './Pages/Home';
import Login from './Pages/Login';
import Registration from './Pages/Registration';
import DoctorNavbar from './Pages/doctor/DoctorNavbar.js';
import RegistraturaNavbar from './Pages/registratura/RegistraturaNavbar';

function App() {

  const [user, setUser] = useState(localStorage.getItem('userMedical'));
  const [jwt, setJwt] = useState(localStorage.getItem('jwtToken'));

  const login = (user, jwt) => {

      localStorage.setItem('jwtToken', jwt);
      localStorage.setItem('userMedical', JSON.stringify(user));
      setJwt(jwt);
      setUser(user);

      console.log(JSON.stringify(user));
  }

  return (
    <Router>
        <div>
          <Routes>
            <Route path="/*" element={<Home />} />
            <Route path='/login' element={<Login login={login}/>}/>
            <Route path='/registration' element={<Registration login={login}/>}/>
            <Route path='/registratura/*' element={<RegistraturaNavbar jwt={jwt}/>}/>
            <Route path='/doctor/*' element={<DoctorNavbar jwt={jwt}/>}/>
          </Routes>
        </div>
      </Router>
  );
}

export default App;
