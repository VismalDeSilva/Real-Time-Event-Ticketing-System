import React, { useContext } from 'react'
import styles from './style.module.scss'
import VendorProfile from '../../Components/VendorProfile'
import { UserContext } from '../Auth/UserContext';
import CustomerProfile from '../../Components/CustomerProfile';
import { Link } from 'react-router-dom';

import Navbar from '../../Components/Navbar'

const Profile = () => {
    const { userRole } = useContext(UserContext);
    console.log(userRole);

    if (userRole === 'customer') {
        return (
            <div>
                <CustomerProfile />
            </div>
        );
    } else if (userRole === 'vendor') {
        return (
            <div>
                <VendorProfile />
            </div>
        );
    } else {
        return (
            <div>
                <Navbar />
                <div className={styles.container}>
                    <div className={styles.profileContainer}>
                        <h2>Please login first  </h2>
                        <Link to="/customer/login"><button style={{backgroundColor: '#00e676'}}>Customer Login</button></Link>
                        <Link to="/vendor/login"><button style={{backgroundColor: '#FFBF00'}}>Vendor Login</button></Link>
                    </div>
                </div>
            </div>
        );
    }
}

export default Profile

