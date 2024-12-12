import React, { useContext, useState } from 'react'
import styles from './style.module.scss'
import { Helmet } from 'react-helmet'
import { Link, useNavigate, useParams } from 'react-router-dom'

import Navbar from '../Navbar'

import profileImg from '../../Assets/profileImg.png'
import { UserContext } from '../../Pages/Auth/UserContext'
import axios from 'axios'

const VendorProfile = () => {
    const { userId, logout } = useContext(UserContext);
    const [vendor, setVendor] = useState(null);
    const navigate = useNavigate();

    const dateFormatter = (date) => {
        const eventDate = new Date(date);
        return eventDate.toLocaleString('default', {
            day: 'numeric',
            month: 'long',
            year: 'numeric'
        });
    }

    const fetchVendorDetails = async () => {
        try {
            const apiUrl = `http://localhost:8080/api/vendors/id/${userId}`; // Replace with your API endpoint
            const response = await axios.get(apiUrl);
            if (response.status === 500) {
                return null;
            }
            const vendor = response.data;
            setVendor(vendor);
        } catch (error) {
            console.error('Error fetching vendor details:', error);
            return null;
        }
    }

    useState(() => {
        fetchVendorDetails();
    }, []);

    const handleLogout = () => {
        logout();
        navigate('/'); // redirect to homepage after logout
    };

    const handleReleaseTicket = (eventId) => {
        console.log(eventId)
        try {
            const apiUrl = `http://localhost:8080/api/events/${eventId}/start-ticketing`; // Replace with your API endpoint
            const response = axios.post(apiUrl);

            if (response.status === 500) {
                return null;
            }
            alert('Ticket releasing process started.');
            console.log('Ticket released successfully:', response.data);
        } catch (error) {
            console.error('Error releasing ticket:', error);
        }
    };


    return (
        <div className={styles.container}>
            <Helmet><title>Profile</title></Helmet>
            <Navbar />
            <div className={styles.vendorContainer}>
                <h1>Vendor Profile Page</h1>
                <img src={profileImg} alt="profileImg" />
                <ul>
                    <li><p>Name :</p><span>{vendor?.name}</span></li>
                    <li><p>Email :</p><span>{vendor?.contactEmail}</span></li>
                    <li><p>Phone Number :</p><span>{vendor?.contactPhone}</span></li>
                </ul>
                <Link to="/vendor/create-event" className={styles.buttonContainer}>
                    <button className={styles.createEvent}>Create Event</button>
                </Link>
                <div className={styles.eventsContainer}>
                    <h2>Events</h2>
                    <div className={styles.eventContainer}>
                        {vendor?.events.length > 0 ? (
                            vendor?.events.map((event) => {
                                return (
                                    <div className={styles.event}>
                                        <h3>{event.name}</h3>
                                        <p>{dateFormatter(event.eventDate)}</p>
                                        <p>{event.location}</p>
                                        <div className={styles.buttonContainer}>
                                            <button style={{ backgroundColor: '#00e676' }} onClick={() => handleReleaseTicket(event.id)}>Release Tickets</button>
                                            <Link to={`/event/${event.id}`}><button style={{ backgroundColor: '#FFBF00' }}>View Event</button></Link>
                                        </div>
                                    </div>
                                )
                            })
                        ) : (
                            <p>No Events Found</p>
                        )}
                    </div>
                </div>
                <div className={styles.buttonContainer}>
                    <button className={styles.logoutButton} onClick={handleLogout}>Logout</button>
                </div>
            </div>
        </div>
    )
}

export default VendorProfile