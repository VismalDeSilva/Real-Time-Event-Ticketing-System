import { createBrowserRouter, RouterProvider } from 'react-router-dom';

import Home from './Pages/Home';
import Events from './Pages/Events';
import EventPage from './Pages/Event';
import Configurator from './Pages/Configurator';
import Profile from './Pages/Profile';
import CustomerLogin from './Pages/LoginSignup/customerLogin';
import VendorLogin from './Pages/LoginSignup/vendorLogin';
import CustomerSignup from './Pages/LoginSignup/customerSignup';
import VendorSignup from './Pages/LoginSignup/vendorSignup';
import CreateEvent from './Pages/CreateEvent';

const router = createBrowserRouter([
  {
    path: "/",
    element: <Home />,
  },
  {
    path: "/configurator",
    element: <Configurator />,
  },
  {
    path: "/event/:id",
    element: <EventPage />,
  },
  {
    path: "/events",
    element: <Events />,
  },
  {
    path: "/profile",
    element: <Profile />,
  },
  {
    path: "/customer/login",
    element: <CustomerLogin />,
  },
  {
    path: "/vendor/login",
    element: <VendorLogin />,
  },
  {
    path: "/customer/signup",
    element: <CustomerSignup />,
  },
  {
    path: "/vendor/signup/",
    element: <VendorSignup />,
  },
  {
    path: "/vendor/create-event",
    element: <CreateEvent />,
  },
  {
    path: "*",
    element: <Home />,
  },
]);


function App() {
  return (
    <RouterProvider router={router} />
  );
}

export default App;
