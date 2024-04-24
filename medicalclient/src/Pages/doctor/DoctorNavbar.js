import { Container } from 'react-bootstrap';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import { Route, Routes } from 'react-router-dom';
import History from './History.js';
import Patients from './Patients.js';
import Session from './Session.js';
import ClientCreate from '../registratura/ClientCreate.js';

function BasicExample({ jwt }) {
    return (
        <div>
            <Navbar bg="#000" expand="lg">
                <Container>
                    <Navbar.Toggle aria-controls="basic-navbar-nav" />
                    <Navbar.Collapse id="basic-navbar-nav">
                        <Nav>
                            <Nav.Link href="/doctor/session">Посещение</Nav.Link>
                            <Nav.Link href="/doctor/history">История</Nav.Link>
                            <Nav.Link href="/doctor/patients">Пациенты</Nav.Link>
                            <Nav.Link href="/doctor/patient">Зарегистрировать</Nav.Link>
                        </Nav>
                    </Navbar.Collapse>
                </Container>
            </Navbar>
            <Routes>
                <Route path='/*' element={<Session jwt={jwt}/>} />
                <Route path='/Session' element={<Session jwt={jwt}/>} />
                <Route path='/history' element={<History jwt={jwt}/>}/>
                <Route path='/patients' element={<Patients jwt={jwt}/>}/>
                <Route path='/patient' element={<ClientCreate jwt={jwt}/>}/>
            </Routes>
        </div>
    );
}

export default BasicExample;